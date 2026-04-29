package com.api.water_sytem_management_java.models.payment;

import com.api.water_sytem_management_java.controllers.dtos.payment.PaymentOutput;
import com.api.water_sytem_management_java.models.customer.Customer;
import com.api.water_sytem_management_java.models.user.User;
import jakarta.persistence.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Entity
@Table(name = "tb_wsm_payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private Double amount;
    private Double tax;
    private int unitPrice;
    private String referenceMonth;
    private byte numMonths;
    private String paymentMethod;
    private Boolean confirmed;

    private final LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // 🔥 NOVO → USER QUE CRIOU
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "reference_code", unique = true)
    private String referenceCode;
    public Payment() {}

    public Payment(Customer customer,
                   Double tax,
                   Double amount,
                   byte numMonths,
                   String paymentMethod,
                   Boolean confirmed) {
        this.customer = customer;
        this.amount = amount;
        this.numMonths = numMonths;
        this.paymentMethod = paymentMethod;
        this.confirmed = confirmed;
        this.tax = tax;
        this.unitPrice = customer.getMonthlyFee();
    }

    public void dowGradeMonthsOnDebt() {
        if (customerHasDebt() && !isAmountGreaterThanDebt()) {
            customer.updateDebt(numMonths);
        }


    }

    public void upGradeMonthsOnDebt() {
        customer.updateDebt((byte) -1);
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }
    public boolean customerHasDebt() {
        return customer.hasOutstandingDebt();
    }

    public boolean isAmountGreaterThanDebt() {
        return customer.isValueGreaterThanDebt(numMonths);
    }

    public PaymentOutput toOutput() {

        var c = this.customer;

        return new PaymentOutput(
                id,
                c.getId(),
                referenceCode,

                // 🔥 CUSTOMER
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

                createdBy != null ? UUID.fromString(createdBy.getId()) : null,
                createdBy != null ? createdBy.getName() : "Sistema"
        );
    }

    // GETTERS / SETTERS

    public UUID getId() { return id; }

    public Customer getCustomer() { return customer; }

    public void setCustomer(Customer customer) { this.customer = customer; }

    public Double getAmount() { return amount; }

    public void setAmount(Double amount) { this.amount = amount; }

    public Double getTax() { return tax; }

    public void setTax(Double tax) { this.tax = tax; }

    public int getUnitPrice() { return unitPrice; }

    public byte getNumMonths() { return numMonths; }

    public void setNumMonths(byte numMonths) { this.numMonths = numMonths; }

    public String getPaymentMethod() { return paymentMethod; }

    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public Boolean getConfirmed() { return confirmed; }

    public void setConfirmed(Boolean confirmed) { this.confirmed = confirmed; }

    public String getReferenceMonth() { return referenceMonth; }

    public void setReferenceMonth(String referenceMonth) { this.referenceMonth = referenceMonth; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public User getCreatedBy() { return createdBy; }

    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }

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

}