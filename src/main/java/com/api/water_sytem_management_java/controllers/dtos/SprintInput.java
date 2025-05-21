package com.api.water_sytem_management_java.controllers.dtos;

import com.api.water_sytem_management_java.models.Sprint;

public record SprintInput(String code, String descricao) {
    public Sprint toSprint() {
        return new Sprint(code, descricao);
    }
}