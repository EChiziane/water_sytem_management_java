package com.api.water_sytem_management_java.repositories.payment;

import com.api.water_sytem_management_java.models.payment.CustomerPaymentInvoice;
import com.api.water_sytem_management_java.models.payment.Payment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerPaymentInvoiceRepository extends JpaRepository<CustomerPaymentInvoice, UUID> {
    CustomerPaymentInvoice findByPaymentId(UUID paymentId);
    List<CustomerPaymentInvoice> findAll(Sort sort);
}
