package com.api.water_sytem_management_java.controllers.dtos.customer;

import com.api.water_sytem_management_java.models.customer.Customer;

import java.math.BigDecimal;


public record CustomerInput(
        String name,
        String contact,
        String address,
        CustomerStatus status,
        byte valve,
        byte monthsInDebt,
       int monthlyFee) {

    public Customer toCustomer() {
        return new Customer(name,
                contact,
                address,
                status,
                valve,
                monthsInDebt,
                monthlyFee);
    }
}
