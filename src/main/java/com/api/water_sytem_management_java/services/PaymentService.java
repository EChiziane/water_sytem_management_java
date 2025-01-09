package com.api.water_sytem_management_java.services;


import com.api.water_sytem_management_java.models.Payment;
import com.api.water_sytem_management_java.repositories.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(UUID id) {
        return paymentRepository.findById(id);
    }

    @Transactional
    public void deletePayment(UUID id) {
        paymentRepository.deleteById(id);
    }

    public static String getMonthsToPay(int monthsToPay, int monthsInDebt) {
        if (monthsToPay <= 0 || monthsInDebt <= 0) {
            throw new IllegalArgumentException("Os meses a pagar e os meses em dívida devem ser maiores que 0.");
        }

        LocalDate currentDate = LocalDate.now();

        // Se estivermos antes do dia 10, ignorar o mês atual nos cálculos
        boolean ignoreCurrentMonth = currentDate.getDayOfMonth() <= 10;
        if (ignoreCurrentMonth) {
            monthsInDebt += 1; // Ajustar para ignorar o mês atual
        }

        if (monthsToPay > monthsInDebt) {
            throw new IllegalArgumentException("Os meses a pagar não podem ser maiores que os meses em dívida.");
        }

        StringBuilder monthsString = new StringBuilder();

        // Determina o mês mais antigo em dívida
        LocalDate oldestDebtMonth = currentDate.minusMonths(monthsInDebt);

        for (int i = 0; i < monthsToPay; i++) {
            LocalDate monthToPay = oldestDebtMonth.plusMonths(i);
            String monthName = monthToPay.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
            int year = monthToPay.getYear();

            monthsString.append(monthName.substring(0, 1).toUpperCase())
                    .append(monthName.substring(1).toLowerCase())
                    .append("-")
                    .append(year);

            if (i < monthsToPay - 1) {
                monthsString.append(" | ");
            }
        }

        return monthsString.toString();
    }



}
