package com.api.water_sytem_management_java.controllers;


import com.api.water_sytem_management_java.models.Invoice;
import com.api.water_sytem_management_java.services.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<Invoice> saveInvoice(@RequestBody Invoice invoice) {
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceService.saveInvoice(invoice));
    }

    @GetMapping
    public ResponseEntity<List<Invoice>> getInvoices() {
        return ResponseEntity.status(HttpStatus.OK).body(invoiceService.getInvoices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Invoice>> getInvoiceById(@PathVariable UUID id) {
        Optional<Invoice> invoice = invoiceService.getInvoiceById(id);
        if (invoice.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(invoice);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable UUID id) {
        if (invoiceService.getInvoiceById(id).isPresent()) {
            invoiceService.deleteInvoice(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}