package com.api.water_sytem_management_java.services;


import com.api.water_sytem_management_java.models.Invoice;
import com.api.water_sytem_management_java.repositories.InvoiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Transactional
    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getInvoices() {
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> getInvoiceById(UUID id) {
        return invoiceRepository.findById(id);
    }

    @Transactional
    public void deleteInvoice(UUID id) {
        invoiceRepository.deleteById(id);
    }
}