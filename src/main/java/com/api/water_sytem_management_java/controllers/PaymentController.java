package com.api.water_sytem_management_java.controllers;

import com.api.water_sytem_management_java.models.Payment;
import com.api.water_sytem_management_java.services.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Payment> savePayment(@RequestBody Payment payment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.savePayment(payment));
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getPayments() {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getPayments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Payment>> getPaymentById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getPaymentById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable UUID id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}