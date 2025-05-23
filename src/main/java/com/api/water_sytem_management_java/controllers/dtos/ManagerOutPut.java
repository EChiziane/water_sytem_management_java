package com.api.water_sytem_management_java.controllers.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record ManagerOutPut(UUID id,
                            String name,
                            String contact,
                            String address,
                            ManagerStatus status,
                            LocalDateTime createdAt
) {
}
