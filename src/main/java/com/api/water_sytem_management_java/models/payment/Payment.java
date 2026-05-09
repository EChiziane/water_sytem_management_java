package com.api.water_sytem_management_java.models.payment;

import com.api.water_sytem_management_java.controllers.dtos.payment.PaymentOutput;
import com.api.water_sytem_management_java.models.MonthlyCharge;
import com.api.water_sytem_management_java.models.customer.Customer;
import com.api.water_sytem_management_java.models.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_wsm_payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // =====================================================
    // FINANCIAL
    // =====================================================

    private Double amount;

    private Double tax;

    private int unitPrice;

    private String referenceMonth;

    private byte numMonths;

    private String paymentMethod;

    private Boolean confirmed;

    private final LocalDateTime createdAt =
            LocalDateTime.now();

    // =====================================================
    // AUDIT
    // =====================================================
    /**
     * Data real do pagamento.
     */
    private LocalDateTime paymentDate =
            LocalDateTime.now();

    // =====================================================
    // CUSTOMER
    // =====================================================
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "customer_id",
            nullable = false
    )
    private Customer customer;

    // =====================================================
    // USER
    // =====================================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    // =====================================================
    // RECEIPT
    // =====================================================

    @Column(
            name = "reference_code",
            unique = true
    )
    private String referenceCode;

    // =====================================================
    // MONTHS PAID
    // =====================================================

    /**
     * Meses liquidados por este pagamento.
     * <p>
     * Ex:
     * Janeiro
     * Fevereiro
     * Março
     */
    @JsonIgnore
    @OneToMany(
            mappedBy = "payment",
            cascade = CascadeType.ALL
    )
    private List<MonthlyCharge> months =
            new ArrayList<>();

    // =====================================================
    // CONSTRUCTORS
    // =====================================================

    public Payment() {
    }

    public Payment(
            Customer customer,
            Double tax,
            Double amount,
            byte numMonths,
            String paymentMethod,
            Boolean confirmed
    ) {

        this.customer = customer;

        this.amount = amount;

        this.numMonths = numMonths;

        this.paymentMethod = paymentMethod;

        this.confirmed = confirmed;

        this.tax = tax;

        this.unitPrice =
                customer.getMonthlyFee();

        this.paymentDate =
                LocalDateTime.now();
    }

    // =====================================================
    // BUSINESS LOGIC
    // =====================================================

    public void addMonth(MonthlyCharge month) {

        this.months.add(month);

        month.setPayment(this);

        month.setPaid(true);

        month.setPaidAt(LocalDateTime.now());
    }

    // =====================================================
    // OUTPUT
    // =====================================================

    public PaymentOutput toOutput() {

        var c = this.customer;

        return new PaymentOutput(

                id,

                c.getId(),

                referenceCode,

                c.getName(),

                c.getContact(),

                c.getAddress(),

                c.getValve(),

                c.getMonthlyFee(),

                amount,

                tax,

                unitPrice,

                confirmed != null && confirmed,

                referenceMonth,

                numMonths,

                createdAt,

                paymentMethod,

                createdBy != null
                        ? UUID.fromString(
                        createdBy.getId()
                )
                        : null,

                createdBy != null
                        ? createdBy.getName()
                        : "Sistema"
        );
    }

    // =====================================================
    // GETTERS & SETTERS
    // =====================================================

    public UUID getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getReferenceMonth() {
        return referenceMonth;
    }

    public void setReferenceMonth(
            String referenceMonth
    ) {
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

    public void setPaymentMethod(
            String paymentMethod
    ) {
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(
            String referenceCode
    ) {
        this.referenceCode = referenceCode;
    }

    public List<MonthlyCharge> getMonths() {
        return months;
    }

    public void setMonths(
            List<MonthlyCharge> months
    ) {
        this.months = months;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(
            LocalDateTime paymentDate
    ) {
        this.paymentDate = paymentDate;
    }
}