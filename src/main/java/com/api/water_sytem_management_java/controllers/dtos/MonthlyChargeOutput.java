package com.api.water_sytem_management_java.controllers.dtos;

import java.util.UUID;

public record MonthlyChargeOutput(
        UUID id,
        String customerName,
        String referenceMonth,
        boolean paid
) {}