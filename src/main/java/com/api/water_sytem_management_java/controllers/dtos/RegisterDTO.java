package com.api.water_sytem_management_java.controllers.dtos;


import com.api.water_sytem_management_java.models.user.User;
import com.api.water_sytem_management_java.models.user.UserRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public record RegisterDTO(String name, String email,String phone,String login, String password, UserRole role) {

    public User toUser(){
        String encryptedPassword = new BCryptPasswordEncoder().encode(password);
        return  new User(login,encryptedPassword,role,email,phone,name);
    }


}