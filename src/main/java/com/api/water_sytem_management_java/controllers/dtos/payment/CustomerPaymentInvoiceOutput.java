package com.api.water_sytem_management_java.controllers.dtos.payment;

import java.util.UUID;

public record CustomerPaymentInvoiceOutput(
        UUID id,
        UUID customerId,
        String customerName,
        Double amount,
        Double tax,
        Double unitPrice
) {


}
