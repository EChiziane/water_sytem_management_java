package com.api.water_sytem_management_java.services;


import com.api.water_sytem_management_java.models.Payment;
import com.api.water_sytem_management_java.repositories.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
