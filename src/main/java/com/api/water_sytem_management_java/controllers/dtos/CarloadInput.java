package com.api.water_sytem_management_java.controllers.dtos;

import com.api.water_sytem_management_java.models.CarLoad;

public record CarloadInput(
        String ClientName,
        String PhoneNumber,
        String MaterialName,
        String Destination,
        Double totalRevenue,
        Double totalExpenses

) {
    public CarLoad toCarLoad() {
        return  new CarLoad(
               ClientName,
                PhoneNumber,
                MaterialName,
                Destination,
                totalRevenue,
                totalExpenses
        );
    }
}
