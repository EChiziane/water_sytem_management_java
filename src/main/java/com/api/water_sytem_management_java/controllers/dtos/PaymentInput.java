package com.api.water_sytem_management_java.controllers.dtos;

import com.api.water_sytem_management_java.models.Customer;
import com.api.water_sytem_management_java.models.Payment;

import java.util.UUID;

public record PaymentInput(
        UUID customerId,
        Double amount,
        byte numMonths,
        String paymentMethod,
        Boolean confirmed
) {

    public Payment toPayment(Customer customer) {
        return new Payment(customer, amount, numMonths, paymentMethod, confirmed);
    }


}