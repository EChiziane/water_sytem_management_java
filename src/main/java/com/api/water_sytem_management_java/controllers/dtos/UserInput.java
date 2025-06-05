package com.api.water_sytem_management_java.controllers.dtos;


import com.api.water_sytem_management_java.models.UserStatus;
import com.api.water_sytem_management_java.models.user.User;
import com.api.water_sytem_management_java.models.user.UserRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public record UserInput(String name, String email, String phone, String password, UserRole role, UserStatus status) {

    public User toUser() {
        String encryptedPassword = new BCryptPasswordEncoder().encode(password);
        return new User( encryptedPassword, role, email, phone, name, status);
    }


}