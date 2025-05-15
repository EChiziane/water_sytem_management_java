package com.api.water_sytem_management_java.controllers.dtos;

import com.api.water_sytem_management_java.models.CustomerStatus;

public record ManagerOutPut(
        String name,
        String contact,
        String address,
        String status
) {
}
