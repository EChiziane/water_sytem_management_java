package com.api.water_sytem_management_java.models.customer;

import com.api.water_sytem_management_java.controllers.dtos.customer.CustomerOutput;
import com.api.water_sytem_management_java.controllers.dtos.customer.CustomerStatus;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_wsm_customers")
public class Customer implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final LocalDateTime createdAt = LocalDateTime.now();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String code;

    private String name;
    private String contact;
    private String address;

    @Enumerated(EnumType.STRING)
    private CustomerStatus status;

    private byte valve;
    private int monthlyFee;
    private byte monthsInDebt;

    public Customer() {}

    public Customer(String name,
                    String contact,
                    String address,
                    CustomerStatus status,
                    byte valve,
                    byte monthsInDebt,
                    int monthlyFee) {
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.status = status;
        this.valve = valve;
        this.monthsInDebt = monthsInDebt;
        this.monthlyFee = monthlyFee;
    }

    public CustomerOutput toCustomerOutput() {
        return new CustomerOutput(
                id,
                code,
                name,
                contact,
                address,
                status,
                valve,
                monthsInDebt,
                monthlyFee,
                createdAt
        );
    }

    // Getters & Setters

    public UUID getId() { return id; }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getContact() { return contact; }

    public void setContact(String contact) { this.contact = contact; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public CustomerStatus getStatus() { return status; }



    public void setId(UUID id) { this.id = id; }

    public boolean hasOutstandingDebt() { return monthsInDebt > 0; }
    public boolean isValueGreaterThanDebt(byte value) { return value > monthsInDebt; }
    public void updateDebt(byte value) { monthsInDebt -= value; }

    public void setStatus(CustomerStatus status) { this.status = status; }

    public byte getValve() { return valve; }

    public void setValve(byte valve) { this.valve = valve; }

    public int getMonthlyFee() { return monthlyFee; }

    public void setMonthlyFee(int monthlyFee) { this.monthlyFee = monthlyFee; }

    public byte getMonthsInDebt() { return monthsInDebt; }

    public void setMonthsInDebt(byte monthsInDebt) { this.monthsInDebt = monthsInDebt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}