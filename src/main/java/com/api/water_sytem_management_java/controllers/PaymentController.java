package com.api.water_sytem_management_java.controllers;

import com.api.water_sytem_management_java.controllers.dtos.payment.PaymentInput;
import com.api.water_sytem_management_java.controllers.dtos.payment.PaymentOutput;
import com.api.water_sytem_management_java.models.customer.Customer;
import com.api.water_sytem_management_java.models.payment.Payment;
import com.api.water_sytem_management_java.repositories.customer.CustomerRepository;
import com.api.water_sytem_management_java.repositories.payment.PaymentRepository;
import com.api.water_sytem_management_java.services.payment.CustomerPaymentInvoiceService;
import com.api.water_sytem_management_java.services.payment.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final CustomerRepository customerRepository;
    private final CustomerPaymentInvoiceService customerPaymentInvoiceService;
    private final PaymentRepository paymentRepository;


    public PaymentController(PaymentService paymentService,
                             CustomerRepository customerRepository, CustomerPaymentInvoiceService customerPaymentInvoiceService,
                             PaymentRepository paymentRepository) {
        this.paymentService = paymentService;
        this.customerRepository = customerRepository;
        this.customerPaymentInvoiceService = customerPaymentInvoiceService;
        this.paymentRepository = paymentRepository;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updateExistingPayment(@PathVariable UUID id, @RequestBody PaymentInput paymentInput) {
        Optional<Customer> customerOptional = customerRepository.findById(paymentInput.customerId());
        if (customerOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Customer customer = customerOptional.get();

        Payment updatedPayment = paymentService.updateExistingPayment(id, paymentInput, customer);
        return ResponseEntity.ok(updatedPayment);
    }
    @PostMapping
    public ResponseEntity<Payment> createNewPayment(@RequestBody PaymentInput paymentInput) throws IOException {
        Customer customer = customerRepository.findById(paymentInput.customerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Payment payment = paymentInput.toPayment(customer);
        customerPaymentInvoiceService.generateCustomerInvoice(paymentRepository.save(payment).getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }
    @GetMapping
    public ResponseEntity<List<PaymentOutput>> fetchAllPayments() {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.fetchAllPayments());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<PaymentOutput>> fetchPaymentsByCustomerId(@PathVariable UUID customerId) {
        List<PaymentOutput> payments = paymentService.fetchPaymentsByCustomerId(customerId);
        return ResponseEntity.ok(payments);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Payment>> fetchPaymentById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.fetchPaymentById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removePaymentById(@PathVariable UUID id) {
        paymentService.removePaymentById(id);
        return ResponseEntity.noContent().build();
    }

}
