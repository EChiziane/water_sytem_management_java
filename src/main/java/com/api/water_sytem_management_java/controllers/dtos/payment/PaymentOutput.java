package com.api.water_sytem_management_java.controllers.dtos.payment;

import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentOutput(
        UUID id,
        String referenceCode,
        UUID customerId,
        String customerName,
        String customerContact,
        String customerAddress,
        byte customerValve,
        int monthlyFee,

        Double amount,
        Double tax,
        int unitPrice,
        boolean confirmed,
        String referenceMonth,
        byte numMonths,
        LocalDateTime paymentDate,
        String paymentMethod
) {}