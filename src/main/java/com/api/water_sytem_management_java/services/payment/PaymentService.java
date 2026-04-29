package com.api.water_sytem_management_java.services.payment;


import com.api.water_sytem_management_java.controllers.dtos.payment.PaymentInput;
import com.api.water_sytem_management_java.controllers.dtos.payment.PaymentOutput;
import com.api.water_sytem_management_java.infra.security.SecurityUtils;
import com.api.water_sytem_management_java.models.customer.Customer;
import com.api.water_sytem_management_java.models.payment.Payment;
import com.api.water_sytem_management_java.repositories.payment.PaymentRepository;
import com.api.water_sytem_management_java.services.EmailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final EmailService emailService;


    @Autowired
    public PaymentService(PaymentRepository paymentRepository, EmailService emailService) {
        this.paymentRepository = paymentRepository;
        this.emailService = emailService;

    }

    public static String buildMonthsDescription(int monthsToPay, int monthsInDebt) {

        LocalDate today = LocalDate.now();

        if (monthsToPay > monthsInDebt) {
            throw new IllegalArgumentException("Meses a pagar > dívida");
        }

        // 🔥 CORREÇÃO REAL (SEM DIA 5)
        LocalDate oldestDebtMonth = today.minusMonths(monthsInDebt);

        return IntStream.range(0, monthsToPay)
                .mapToObj(i -> oldestDebtMonth.plusMonths(i))
                .map(date -> {
                    String m = date.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "PT"));
                    return m.substring(0,1).toUpperCase() + m.substring(1).toLowerCase()
                            + "-" + date.getYear();
                })
                .collect(Collectors.joining(" | "));
    }


    public List<PaymentOutput> fetchPaymentsByCustomerId(UUID customerId) {
        return paymentRepository.findByCustomerId(Sort.by(Sort.Direction.DESC,"createdAt"),customerId )
                .stream()
                .map(this::toPaymentOutput)
                .collect(Collectors.toList());
    }


    private String generateReferenceCode() {
        int year = LocalDate.now().getYear();

        long count = paymentRepository.count(); // ou melhor: count por ano

        return String.format("WSM-%d-%06d", year, count + 1);
    }

    @Transactional
    public Payment validateAndSavePayment(Payment payment) throws IOException {

        if (!payment.customerHasDebt()) {
            throw new RuntimeException("Customer has no debt");
        }

        if (payment.isAmountGreaterThanDebt()) {
            throw new RuntimeException("Your months are more than you have");
        }

        // ✅ GERAR REFERENCE MONTH (AQUI!)
        payment.setReferenceMonth(
                buildMonthsDescription(
                        payment.getNumMonths(),
                        payment.getCustomer().getMonthsInDebt()
                )
        );

        payment.dowGradeMonthsOnDebt();

        payment.setReferenceCode(generateReferenceCode());
        payment.setConfirmed(true);

        var user = SecurityUtils.getLoggedUser();
        payment.setCreatedBy(user);

        return paymentRepository.save(payment);
    }

    public List<PaymentOutput> fetchAllPayments() {
     //   emailService.enviarEmailTexto("eddybruno43@gmail.com", "Email de Agua", "Alguem Ta recarregando a lista de agua");
        return paymentRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(this::toPaymentOutput)
                .collect(Collectors.toList());
    }

    private PaymentOutput toPaymentOutput(Payment payment) {

        return payment.toOutput();
    }

    public Optional<PaymentOutput> fetchPaymentById(UUID id) {
        return paymentRepository.findById(id).map(this::toPaymentOutput);
    }

    @Transactional
    public void removePaymentById(UUID id) {
        paymentRepository.deleteById(id);
    }

    @Transactional
    public Payment updateExistingPayment(UUID id, PaymentInput paymentInput, Customer customer) {
        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        existingPayment.setAmount(paymentInput.amount());
        existingPayment.setNumMonths(paymentInput.numMonths());
        existingPayment.setPaymentMethod(paymentInput.paymentMethod());
        existingPayment.setConfirmed(paymentInput.confirmed());
        existingPayment.setReferenceMonth(Payment.getReferenceMonth(paymentInput.numMonths()));
        existingPayment.setCustomer(customer);

        return paymentRepository.save(existingPayment);
    }


}
