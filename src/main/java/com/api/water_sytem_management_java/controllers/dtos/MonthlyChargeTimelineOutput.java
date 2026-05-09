package com.api.water_sytem_management_java.controllers.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record MonthlyChargeTimelineOutput(

        UUID id,

        String referenceMonth,

        boolean paid,

        LocalDateTime paidAt,

        Double amount,

        String customerNameSnapshot,

        String customerContactSnapshot,

        String customerAddressSnapshot,

        String valveSnapshot,

        String customerStatusSnapshot,

        UUID paymentId,

        String paymentReferenceCode,

        String paymentMethod,

        String createdByName,

        LocalDateTime paymentDate

) {
}