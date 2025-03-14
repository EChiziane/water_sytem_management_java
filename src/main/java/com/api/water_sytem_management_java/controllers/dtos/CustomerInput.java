package com.api.water_sytem_management_java.controllers.dtos;

import com.api.water_sytem_management_java.models.Customer;
import com.api.water_sytem_management_java.models.CustomerStatus;


public record CustomerInput(
        String name,
        String contact,
        String address,
        CustomerStatus status,
        byte valve,
        byte monthsInDebt) {

    public Customer toCustomer() {
        return new Customer(name,
                contact,
                address,
                status,
                valve,
                monthsInDebt);
    }
}
