package com.api.water_sytem_management_java.controllers.dtos;

import com.api.water_sytem_management_java.models.CustomerStatus;

import java.util.UUID;

public record ManagerOutPut(UUID id,
                            String name,
                            String contact,
                            String address,
                            ManagerStatus status
) {
}
