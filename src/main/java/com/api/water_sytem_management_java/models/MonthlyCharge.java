package com.api.water_sytem_management_java.models;

import com.api.water_sytem_management_java.models.customer.Customer;
import jakarta.persistence.*;

import java.time.YearMonth;
import java.util.UUID;

@Entity
@Table(
        name = "tb_wsm_monthly_charges",
        uniqueConstraints = @UniqueConstraint(columnNames = {"customer_id", "reference_month"})
)
public class MonthlyCharge {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "reference_month", nullable = false)
    private String referenceMonth; // "2026-05"

    private boolean paid;

    public MonthlyCharge() {}

    public MonthlyCharge(Customer customer, String referenceMonth) {
        this.customer = customer;
        this.referenceMonth = referenceMonth;
        this.paid = false;
    }

    // GETTERS / SETTERS

    public UUID getId() { return id; }

    public Customer getCustomer() { return customer; }

    public String getReferenceMonth() { return referenceMonth; }

    public boolean isPaid() { return paid; }

    public void setPaid(boolean paid) { this.paid = paid; }
}