package com.api.water_sytem_management_java.models;

import com.api.water_sytem_management_java.controllers.dtos.DriverStatus;

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
