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

    @Autowired
    private MonthlyChargeRepository monthlyChargeRepository;

    private final EmailService emailService;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, EmailService emailService) {
        this.paymentRepository = paymentRepository;
        this.emailService = emailService;
    }

    // ================== CREATE ==================

    @Transactional
    public Payment validateAndSavePayment(Payment payment) throws IOException {

        var unpaidMonths = monthlyChargeRepository
                .findByCustomerIdAndPaidFalseOrderByReferenceMonthAsc(
                        payment.getCustomer().getId()
                );

        if (unpaidMonths.isEmpty()) {
            throw new RuntimeException("Customer has no debt");
        }

        if (payment.getNumMonths() > unpaidMonths.size()) {
            throw new RuntimeException("Meses a pagar maior que dívida");
        }

        // 🔥 MESES CORRETOS (MAIS ANTIGOS)
        var monthsToPay = unpaidMonths.subList(0, payment.getNumMonths());

        // 🔥 RELAÇÃO REAL
        payment.setMonths(monthsToPay);

        // 🔥 STRING PARA FRONT
        String referenceMonth = monthsToPay.stream()
                .map(MonthlyCharge::getReferenceMonth)
                .collect(Collectors.joining(" | "));

        payment.setReferenceMonth(referenceMonth);

        // 🔥 MARCAR PAGOS
        monthsToPay.forEach(m -> m.setPaid(true));

         unpaidMonths =
                monthlyChargeRepository
                        .findByCustomerIdAndPaidFalseOrderByReferenceMonthAsc(
                                payment.getCustomer().getId()
                        );

         monthsToPay =
                unpaidMonths.subList(
                        0,
                        payment.getNumMonths()
                );

        monthsToPay.forEach(m -> m.setPaid(true));

        payment.setReferenceCode(generateReferenceCode());
        payment.setConfirmed(true);

        var user = SecurityUtils.getLoggedUser();
        payment.setCreatedBy(user);

        return paymentRepository.save(payment);
    }

    // ================== FETCH ==================

    public List<PaymentOutput> fetchAllPayments() {
        return paymentRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(this::toPaymentOutput)
                .collect(Collectors.toList());
    }

    public List<PaymentOutput> fetchPaymentsByCustomerId(UUID customerId) {
        return paymentRepository.findByCustomerId(
                        Sort.by(Sort.Direction.DESC, "createdAt"),
                        customerId
                )
                .stream()
                .map(this::toPaymentOutput)
                .collect(Collectors.toList());
    }

    public Optional<PaymentOutput> fetchPaymentById(UUID id) {
        return paymentRepository.findById(id).map(this::toPaymentOutput);
    }

    @Transactional
    public void removePaymentById(UUID id) {
        paymentRepository.deleteById(id);
    }

    @Transactional
    public Payment updateExistingPayment(UUID id, PaymentInput input, Customer customer) {

        Payment existing = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        existing.setAmount(input.amount());
        existing.setNumMonths(input.numMonths());
        existing.setPaymentMethod(input.paymentMethod());
        existing.setConfirmed(input.confirmed());
        existing.setCustomer(customer);

        return paymentRepository.save(existing);
    }

    private PaymentOutput toPaymentOutput(Payment payment) {
        return payment.toOutput();
    }

    // ================== CODE ==================

    private String generateReferenceCode() {
        int year = LocalDate.now().getYear();
        long count = paymentRepository.count();
        return String.format("WSM-%d-%06d", year, count + 1);
    }
}