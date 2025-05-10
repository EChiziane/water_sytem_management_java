package com.api.water_sytem_management_java.controllers.dtos;

import com.api.water_sytem_management_java.models.Driver;

public record DriverInput(
        String name,
        String contact,
        String carDescription,
        DriverStatus status
      ) {

    public Driver toDriver() {
        return new Driver();
    }
}
