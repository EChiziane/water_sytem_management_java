package com.api.water_sytem_management_java.controllers.dtos;

import com.api.water_sytem_management_java.models.Sprint;

public record SprintInput(String name,String code, String description, SprintStatus status) {
    public Sprint toSprint() {
        return new Sprint(name,code, description, status);
    }
}