package com.api.water_sytem_management_java.controllers;

import com.api.water_sytem_management_java.models.User;

public record UserRegisterInput
        (String name, String userName, String email, String password) {
    public User toUser() {
        return new User(
                name,
                userName,
                email,
                password


        );

    }

}
