package com.api.water_sytem_management_java.controllers.dtos;

import com.api.water_sytem_management_java.models.UserRole;

import java.util.UUID;

public record UserOutPut(UUID id,
                         String name,
                         UserRole role,
                         String userName,
                         String email,
                         String password) {
}
