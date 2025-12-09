package com.api.water_sytem_management_java.controllers;

import com.api.water_sytem_management_java.models.payment.CustomerPaymentInvoice;
import com.api.water_sytem_management_java.models.payment.Payment;
import com.api.water_sytem_management_java.repositories.payment.PaymentRepository;
import com.api.water_sytem_management_java.services.payment.CustomerPaymentInvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/customer_payments_invoice")
public class CustomerPaymentInvoiceController {
    private final PaymentRepository paymentRepository;

    private final CustomerPaymentInvoiceService customerPaymentInvoiceService;

    public CustomerPaymentInvoiceController(PaymentRepository paymentRepository,
                                            CustomerPaymentInvoiceService customerPaymentInvoiceService) {
        this.paymentRepository = paymentRepository;
        this.customerPaymentInvoiceService = customerPaymentInvoiceService;
    }

    @PostMapping("/{paymentId}")
    public ResponseEntity<CustomerPaymentInvoice> generateCustomerPaymentInvoice(@PathVariable UUID paymentId) throws IOException {
        Payment paymentOptional = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        return ResponseEntity.status(HttpStatus.CREATED).body(customerPaymentInvoiceService.generateCustomerInvoice(paymentId));

    }

    @GetMapping
    public ResponseEntity<List<CustomerPaymentInvoice>> listCustomerPaymentsInvoices() {
        return ResponseEntity.status(HttpStatus.OK).body(customerPaymentInvoiceService.listAllCustomerInvoices());
    }


    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadInvoiceFile(@PathVariable UUID id) throws IOException {
        CustomerPaymentInvoice customerPaymentInvoice = customerPaymentInvoiceService.fetchInvoiceByPaymentId(id);
        if (customerPaymentInvoice == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customerPaymentInvoice  not found for the given payment.");
        }

        File file = new File(customerPaymentInvoice.getFilePath());
        if (!file.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found for the given receipt.");
        }

        byte[] fileContent = java.nio.file.Files.readAllBytes(file.toPath());

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + customerPaymentInvoice.getFileName() + "\"")
                .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .body(fileContent);
    }
}
