package com.api.water_sytem_management_java.models;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_customers")
public class Customer implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String contact;
    private String address;
    private CustomerStatus status;
    private Integer valve;
    private Integer monthsInDebt; // Total number of months in debt
    private LocalDateTime createdAt = LocalDateTime.now(); // Use LocalDateTime.now() directly

    public Customer(String name, String contact, String address, CustomerStatus status, Integer valve, Integer monthsInDebt) {
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.status = status;
        this.valve = valve;
        this.monthsInDebt = monthsInDebt;

    }

    public Customer() {

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

    public String getContact() {
        return contact;
    }

    public String getAddress() {
        return address;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public Integer getValve() {
        return valve;
    }

    public Integer getMonthsInDebt() {
        return monthsInDebt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


}
