package com.api.water_sytem_management_java.infra.security;


import com.api.water_sytem_management_java.models.user.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static User getLoggedUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}