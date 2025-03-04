package com.api.water_sytem_management_java.controllers.dtos;

import com.api.water_sytem_management_java.models.Customer;
import com.api.water_sytem_management_java.models.CustomerStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerInput(
         String name,
         String contact,
         String address,
         CustomerStatus status,
         Integer valve,
         Integer monthsInDebt){

    public Customer toCustomer() {
     return  new Customer(name,
             contact,
             address,
             status,
             valve,
             monthsInDebt);
    }
}
