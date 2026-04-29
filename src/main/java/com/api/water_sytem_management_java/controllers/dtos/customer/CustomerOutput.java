package com.api.water_sytem_management_java.controllers.dtos.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerOutput(
        UUID id,
        String code,
        String name,
        String contact,
        String address,
        CustomerStatus status,
        byte valve,
        byte monthsInDebt,
        int monthlyFee,
        LocalDateTime createdAt
) {}