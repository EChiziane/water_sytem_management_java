package com.api.water_sytem_management_java.controllers.dtos.payment;

import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentOutput(
        UUID id,
        UUID customerId,
        String referenceCode,

        // 🔥 CUSTOMER DATA COMPLETO
        String customerName,
        String customerContact,
        String customerAddress,
        byte customerValve,
        int monthlyFee,

        Double amount,
        Double tax,
        int unitPrice,
        Boolean confirmed,
        String referenceMonth,
        byte numMonths,
        LocalDateTime paymentDate,
        String paymentMethod,

        UUID createdBy,
        String createdByName
) {}