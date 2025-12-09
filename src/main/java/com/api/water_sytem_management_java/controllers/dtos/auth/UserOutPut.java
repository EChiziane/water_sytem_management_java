package com.api.water_sytem_management_java.controllers.dtos.auth;

import com.api.water_sytem_management_java.models.user.UserRole;
import com.api.water_sytem_management_java.models.user.UserStatus;

import java.time.LocalDateTime;

public record UserOutPut(String id, String name, String email, String phone, String login, UserRole role,
                         UserStatus status, LocalDateTime createdAt) {

}
