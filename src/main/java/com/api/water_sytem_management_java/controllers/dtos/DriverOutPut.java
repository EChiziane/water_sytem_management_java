package com.api.water_sytem_management_java.controllers.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record DriverOutPut(
        UUID id,
        String Name,
        String Phone,
        String CarDescription,
        DriverStatus status,
        LocalDateTime createdAt) {
}
