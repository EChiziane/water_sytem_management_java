package com.api.water_sytem_management_java.controllers.dtos.payment;

import java.util.UUID;

public record CustomerPaymentInvoiceInput(
        UUID paymentId
) {
}
