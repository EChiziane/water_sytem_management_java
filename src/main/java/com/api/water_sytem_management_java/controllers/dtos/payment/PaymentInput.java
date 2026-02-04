package com.api.water_sytem_management_java.controllers.dtos.payment;

import com.api.water_sytem_management_java.models.customer.Customer;
import com.api.water_sytem_management_java.models.payment.Payment;

import java.util.UUID;

public record PaymentInput(
        UUID customerId,
        Double amount,
        byte numMonths,
        String paymentMethod,
        Boolean confirmed
) {

    public Payment toPayment(Customer customer) {
        return new Payment(customer,customer.getMonthlyFee()+0.0, amount, numMonths, paymentMethod, confirmed);
    }


}