package com.api.water_sytem_management_java.services.payment;

import com.api.water_sytem_management_java.controllers.dtos.payment.PaymentInput;
import com.api.water_sytem_management_java.controllers.dtos.payment.PaymentOutput;
import com.api.water_sytem_management_java.infra.security.SecurityUtils;
import com.api.water_sytem_management_java.models.MonthlyCharge;
import com.api.water_sytem_management_java.models.customer.Customer;
import com.api.water_sytem_management_java.models.payment.Payment;
import com.api.water_sytem_management_java.repositories.MonthlyChargeRepository;
import com.api.water_sytem_management_java.repositories.payment.PaymentRepository;
import com.api.water_sytem_management_java.services.EmailService;
import com.api.water_sytem_management_java.services.MonthlyChargeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final MonthlyChargeService monthlyChargeService;

    private final MonthlyChargeRepository monthlyChargeRepository;

    private final EmailService emailService;

    @Autowired
    public PaymentService(
            PaymentRepository paymentRepository,
            MonthlyChargeService monthlyChargeService,
            MonthlyChargeRepository monthlyChargeRepository,
            EmailService emailService
    ) {

        this.paymentRepository =
                paymentRepository;

        this.monthlyChargeService =
                monthlyChargeService;

        this.monthlyChargeRepository =
                monthlyChargeRepository;

        this.emailService =
                emailService;
    }

    // =====================================================
    // CREATE PAYMENT
    // =====================================================

    @Transactional
    public Payment validateAndSavePayment(
            Payment payment
    ) throws IOException {

        // ================================================
        // GUARANTEE TIMELINE
        // ================================================

        monthlyChargeService
                .syncCustomerTimeline(
                        payment.getCustomer()
                );

        // ================================================
        // GET REAL UNPAID MONTHS
        // ================================================

        List<MonthlyCharge> unpaidMonths =

                monthlyChargeService
                        .getCustomerUnpaidEntities(
                                payment.getCustomer().getId()
                        );
        // ================================================
        // NO DEBT
        // ================================================

        if (unpaidMonths.isEmpty()) {

            throw new RuntimeException(
                    "Cliente sem dívida"
            );
        }

        // ================================================
        // INVALID MONTH COUNT
        // ================================================

        if (
                payment.getNumMonths()
                        > unpaidMonths.size()
        ) {

            throw new RuntimeException(

                    "Quantidade de meses maior que dívida"
            );
        }

        // ================================================
        // SELECT MONTHS TO PAY
        // ================================================

        List<MonthlyCharge> monthsToPay =

                unpaidMonths.subList(
                        0,
                        payment.getNumMonths()
                );

        // ================================================
        // GENERATE REFERENCE STRING
        // ================================================

        String referenceMonth =

                monthsToPay.stream()

                        .map(
                                MonthlyCharge::getFormattedReference
                        )

                        .collect(
                                Collectors.joining(" | ")
                        );

        payment.setReferenceMonth(
                referenceMonth
        );

        // ================================================
        // GENERATE CODE
        // ================================================

        payment.setReferenceCode(
                generateReferenceCode()
        );

        // ================================================
        // DEFAULT STATUS
        // ================================================

        payment.setConfirmed(true);

        // ================================================
        // LOGGED USER
        // ================================================

        var user =
                SecurityUtils.getLoggedUser();

        payment.setCreatedBy(user);

        // ================================================
        // LINK MONTHS TO PAYMENT
        // ================================================

        for (
                MonthlyCharge month : monthsToPay
        ) {

            payment.addMonth(month);
        }

        // ================================================
        // SAVE PAYMENT FIRST
        // ================================================

        Payment savedPayment =
                paymentRepository.save(payment);

        // ================================================
        // MARK MONTHS AS PAID
        // ================================================

        for (
                MonthlyCharge month : monthsToPay
        ) {

            month.markAsPaid(
                    savedPayment
            );

            monthlyChargeRepository.save(
                    month
            );
        }

        // ================================================
        // OPTIONAL EMAIL
        // ================================================

        /*
        emailService.sendPaymentConfirmation(
                savedPayment
        );
        */

        return savedPayment;
    }

    // =====================================================
    // FETCH ALL
    // =====================================================

    public List<PaymentOutput> fetchAllPayments() {

        return paymentRepository

                .findAll(
                        Sort.by(
                                Sort.Direction.DESC,
                                "createdAt"
                        )
                )

                .stream()

                .map(this::toPaymentOutput)

                .collect(Collectors.toList());
    }

    // =====================================================
    // FETCH BY CUSTOMER
    // =====================================================

    public List<PaymentOutput>
    fetchPaymentsByCustomerId(
            UUID customerId
    ) {

        return paymentRepository

                .findByCustomerId(

                        Sort.by(
                                Sort.Direction.DESC,
                                "createdAt"
                        ),

                        customerId
                )

                .stream()

                .map(this::toPaymentOutput)

                .collect(Collectors.toList());
    }

    // =====================================================
    // FETCH BY ID
    // =====================================================

    public Optional<PaymentOutput>
    fetchPaymentById(UUID id) {

        return paymentRepository
                .findById(id)
                .map(this::toPaymentOutput);
    }

    // =====================================================
    // DELETE PAYMENT
    // =====================================================

    @Transactional
    public void removePaymentById(
            UUID id
    ) {

        // ================================================
        // FIND PAYMENT
        // ================================================

        Payment payment =

                paymentRepository
                        .findById(id)

                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Pagamento não encontrado"
                                )
                        );

        // ================================================
        // GET RELATED MONTHS
        // ================================================

        List<MonthlyCharge> months =

                monthlyChargeRepository
                        .findByPaymentIdOrderByReferenceYearAscReferenceMonthAsc(
                                payment.getId()
                        );

        // ================================================
        // REVERT MONTHS
        // ================================================

        for (
                MonthlyCharge month : months
        ) {

            month.markAsUnpaid();

            monthlyChargeRepository.save(
                    month
            );
        }

        // ================================================
        // DELETE PAYMENT
        // ================================================

        paymentRepository.delete(payment);
    }

    // =====================================================
    // UPDATE PAYMENT
    // =====================================================

    @Transactional
    public Payment updateExistingPayment(
            UUID id,
            PaymentInput input,
            Customer customer
    ) {

        Payment existing =
                paymentRepository
                        .findById(id)

                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Payment not found"
                                )
                        );

        existing.setAmount(
                input.amount()
        );

        existing.setNumMonths(
                input.numMonths()
        );

        existing.setPaymentMethod(
                input.paymentMethod()
        );

        existing.setConfirmed(
                input.confirmed()
        );

        existing.setCustomer(customer);

        return paymentRepository.save(existing);
    }

    // =====================================================
    // OUTPUT
    // =====================================================

    private PaymentOutput toPaymentOutput(
            Payment payment
    ) {

        return payment.toOutput();
    }

    // =====================================================
    // GENERATE CODE
    // =====================================================

    private String generateReferenceCode() {

        int year =
                LocalDate.now().getYear();

        long count =
                paymentRepository.count();

        return String.format(

                "WSM-%d-%06d",

                year,

                count + 1
        );
    }
}