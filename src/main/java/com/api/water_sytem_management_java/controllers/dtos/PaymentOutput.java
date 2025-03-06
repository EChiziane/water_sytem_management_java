package com.api.water_sytem_management_java.controllers.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentOutput(
        UUID id,
        UUID customerId,
        String customerName,
        Double amount,
        boolean status,
        String referenceMonth,

        byte numMonths,
        LocalDateTime paymentDate,
        String paymentMethod) {


    public PaymentOutput(UUID id, UUID customerId, String customerName, Double amount, boolean status, String referenceMonth, byte numMonths, LocalDateTime paymentDate, String paymentMethod) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.amount = amount;
        this.status = status;
        this.referenceMonth = referenceMonth;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.numMonths = numMonths;
    }
}