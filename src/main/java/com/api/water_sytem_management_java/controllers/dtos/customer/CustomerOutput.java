package com.api.water_sytem_management_java.controllers.dtos.customer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CustomerOutput(

        UUID id,

        String code,

        String name,

        String contact,

        String address,

        CustomerStatus status,

        byte valve,

        int monthsInDebt,

        List<String> unpaidMonths,

        int monthlyFee,

        LocalDateTime createdAt
) {}