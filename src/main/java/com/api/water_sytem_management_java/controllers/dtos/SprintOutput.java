package com.api.water_sytem_management_java.controllers.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record SprintOutput(UUID id, String name, String code, String description, SprintStatus status,
                           LocalDateTime createdAt) {
}
