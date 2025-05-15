package com.api.water_sytem_management_java.models;

import com.api.water_sytem_management_java.controllers.dtos.CustomerOutput;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_customers6")
public class Customer implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final LocalDateTime createdAt = LocalDateTime.now(); // Use LocalDateTime.now() directly
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String contact;
    private String address;
    private CustomerStatus status;
    private byte valve;
    private byte monthsInDebt; // Total number of months in debt


    public Customer(String name, String contact, String address, CustomerStatus status, byte valve, byte monthsInDebt) {
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.status = status;
        this.valve = valve;
        this.monthsInDebt = monthsInDebt;

    }


    public Customer() {

    }

    public CustomerOutput CustomerOutput(Customer customer) {
        return new CustomerOutput(
                customer.id,
                customer.name,
                customer.contact,
                customer.address,
                customer.status,
                customer.valve,
                customer.monthsInDebt,
                "",
                customer.createdAt
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte getValve() {
        return valve;
    }

    public void setValve(byte valve) {
        this.valve = valve;
    }

    public byte getMonthsInDebt() {
        return monthsInDebt;
    }

    public void setMonthsInDebt(byte monthsInDebt) {
        this.monthsInDebt = monthsInDebt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean hasOutstandingDebt() {
        return monthsInDebt > 0;
    }

    public boolean isValueGreaterThanDebt(byte value) {
        return value > monthsInDebt;
    }

    public void updateDebt(byte value) {
        monthsInDebt -= value;
    }


}
