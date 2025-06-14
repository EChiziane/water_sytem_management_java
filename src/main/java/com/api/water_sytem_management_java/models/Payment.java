package com.api.water_sytem_management_java.models;

import com.api.water_sytem_management_java.controllers.dtos.PaymentOutput;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Entity
@Table(name = "tb_payment13")
public class Payment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final LocalDateTime createdAt = LocalDateTime.now();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Double amount;
    private String referenceMonth;
    private byte numMonths;
    private String paymentMethod;
    private Boolean confirmed;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // Construtores
    public Payment(Customer customer, Double amount, byte numMonths, String paymentMethod, Boolean confirmed) {
        this.customer = customer;
        this.amount = amount;
        this.numMonths = numMonths;
        this.paymentMethod = paymentMethod;
        this.confirmed = confirmed;
        this.referenceMonth = getReferenceMonth(numMonths);
    }


    public Payment() {
    }

    // Método estático
    public static String getReferenceMonth(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("O número de meses deve ser maior que zero.");
        }

        LocalDate today = LocalDate.now();
        return IntStream.range(0, n)
                .mapToObj(i -> today.minusMonths(n - 1 - i))
                .map(date -> date.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "PT")))
                .collect(Collectors.joining(", "));
    }

    // Métodos de instância
    public PaymentOutput PaymentOutPut(Payment payment) {
        return new PaymentOutput(
                payment.id,
                payment.customer.getId(),
                payment.customer.getName(),
                payment.amount,
                true,
                payment.referenceMonth,
                payment.numMonths,
                payment.createdAt,
                payment.paymentMethod
        );
    }

    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public void dowGradeMonthsOnDebt() {
        if (customerHasDebt() && !isAmountGreaterThanDebt()) {
            customer.updateDebt(numMonths);
        }


    }

    public void upGradeMonthsOnDebt() {
        customer.updateDebt((byte) -1);
    }

    public boolean customerHasDebt() {
        return customer.hasOutstandingDebt();
    }

    public boolean isAmountGreaterThanDebt() {
        return customer.isValueGreaterThanDebt(numMonths);
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReferenceMonth() {
        return referenceMonth;
    }

    public void setReferenceMonth(String referenceMonth) {
        this.referenceMonth = referenceMonth;
    }

    public byte getNumMonths() {
        return numMonths;
    }

    public void setNumMonths(byte numMonths) {
        this.numMonths = numMonths;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}