package com.api.water_sytem_management_java.controllers;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class paymentDto {


    private UUID customerId;

    private Double amount;
    private String paymentDate;
    private String referenceMonth;

    private byte numMonths;
    private String paymentMethod;
    private Boolean confirmed;

    public byte getNumMonths() {
        return numMonths;
    }

    public void setNumMonths(byte numMonths) {
        this.numMonths = numMonths;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getReferenceMonth() {
        return referenceMonth;
    }

    public void setReferenceMonth(String referenceMonth) {
        this.referenceMonth = referenceMonth;
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
}
