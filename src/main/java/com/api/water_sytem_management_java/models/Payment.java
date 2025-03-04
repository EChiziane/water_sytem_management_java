package com.api.water_sytem_management_java.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_payment1")
public class Payment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private Double amount;

    private UUID customerId;

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    private LocalDateTime createdAt = LocalDateTime.now();



    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime paymentDate) {
        this.createdAt = paymentDate;
    }

    public byte getNumMonths() {
        return numMonths;
    }

    public void setNumMonths(byte numMonths) {
        this.numMonths = numMonths;
    }

    private String referenceMonth;
    private byte numMonths;
    private String paymentMethod;
    private Boolean confirmed;

 /*   @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;*/

    public Payment(Double amount, String referenceMonth, String paymentMethod, Boolean confirmed, Customer customer) {
        this.amount = amount;
        this.createdAt = LocalDateTime.now();;
        this.referenceMonth = referenceMonth;
        this.paymentMethod = paymentMethod;
        this.confirmed = confirmed;
      //  this.customer = customer;
    }

    public Payment() {

    }
/*

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


*/

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
