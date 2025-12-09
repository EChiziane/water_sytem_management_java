package com.api.water_sytem_management_java.models.payment;


import com.api.water_sytem_management_java.controllers.dtos.payment.CustomerPaymentInvoiceOutput;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_customer_payment")
public class CustomerPaymentInvoice {
    @Serial
    private static final long serialVersionUID = 1L;
    private final LocalDateTime createdAt = LocalDateTime.now();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String invoiceCode;
    private double subtotal;
    private double taxRate;
    private double tax;
    private double unitPrice;
    private double amount;
    private double total;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private String customerEmail;
    private String customerCode;
    private String monthDescription;
    private byte numMonth;
    private String filePath;
    private String fileName;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    public CustomerPaymentInvoice(String invoiceCode, Payment payment) {
        this.invoiceCode = invoiceCode;
        this.filePath = filePath;
        this.fileName = fileName;
        this.payment = payment;
        this.tax = payment.getTax();
        this.subtotal = payment.getNumMonths() * payment.getUnitPrice();
        this.total = payment.getTax() * (payment.getNumMonths() * payment.getUnitPrice()) + payment.getNumMonths() * payment.getUnitPrice();
        this.unitPrice = payment.getUnitPrice();
        this.amount = 2000;
        this.customerName = payment.getCustomer().getName();
        this.customerAddress = payment.getCustomer().getAddress();
        this.customerPhone = payment.getCustomer().getContact();
        this.customerEmail = "cliente.aguachiziane@gmail.com";
        this.customerCode = payment.getCustomer().getId().toString();
        this.monthDescription = payment.getReferenceMonth();
        this.numMonth = payment.getNumMonths();
    }

    public CustomerPaymentInvoice() {

    }

    public CustomerPaymentInvoiceOutput toCustomerPaymentInvoiceOutput(

    ) {
        return new CustomerPaymentInvoiceOutput(
                this.id,
                this.payment.getCustomer().getId(),
                this.getCustomerName(),
                this.getAmount(),
                this.getTax(),
                this.getUnitPrice()
        );
    }
}

