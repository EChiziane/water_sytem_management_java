package com.api.water_sytem_management_java.controllers.dtos;

import com.api.water_sytem_management_java.models.Manager;

public record ManagerInPut(String name,
                           String contact,
                           String address
                           ) {
    public Manager toManager() {
        return  new Manager(
                name,
                contact,
                address
        );
    }
}
