package com.api.water_sytem_management_java.controllers.dtos;

import com.api.water_sytem_management_java.models.user.UserRole;

import java.time.LocalDateTime;

public record UserOutPut(String nome, String email, String phone, String userName, UserRole role, LocalDateTime createdAt) {

}
