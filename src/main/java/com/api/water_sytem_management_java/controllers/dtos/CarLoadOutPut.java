package com.api.water_sytem_management_java.controllers.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record CarLoadOutPut(
        UUID id,
        String ClientName,
        String PhoneNumber,
        String MaterialName,
        String Destination,
        Double totalRevenue,
        Double totalExpenses,
        LocalDateTime createdAt
) {
}
