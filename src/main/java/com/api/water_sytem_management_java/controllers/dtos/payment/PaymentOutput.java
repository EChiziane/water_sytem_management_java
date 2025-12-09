package com.api.water_sytem_management_java.controllers.dtos.payment;

import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentOutput(
        UUID id,
        UUID customerId,
        String customerName,
        Double amount,
        Double tax,
        Double unitPrice,
        boolean status,
        String referenceMonth,

        byte numMonths,

        LocalDateTime paymentDate,
        String paymentMethod) {


    public PaymentOutput(UUID id,
                         UUID customerId,
                         String customerName,
                         Double amount,
                         Double tax,
                         Double unitPrice,
                         boolean status,
                         String referenceMonth,
                         byte numMonths,
                         LocalDateTime paymentDate,
                         String paymentMethod) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.amount = amount;
        this.status = status;
        this.referenceMonth = referenceMonth;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.numMonths = numMonths;
        this.tax = tax;
        this.unitPrice = unitPrice;
    }
}