package com.api.water_sytem_management_java.dtos;


import com.api.water_sytem_management_java.domain.user.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}