package com.api.water_sytem_management_java.controllers.dtos;


import com.api.water_sytem_management_java.models.user.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}