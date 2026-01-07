package com.api.water_sytem_management_java.controllers.dtos.payment;

import com.api.water_sytem_management_java.models.customer.Customer;
import com.api.water_sytem_management_java.models.payment.Payment;

import java.util.UUID;

public record PaymentInput(
        UUID customerId,
        Double amount,
        Double tax,
        Double unitPrice,
        byte numMonths,
        String paymentMethod,
        Boolean confirmed
) {

    public Payment toPayment(Customer customer) {
        return new Payment(customer, amount, tax, numMonths, paymentMethod, confirmed);
    }


}