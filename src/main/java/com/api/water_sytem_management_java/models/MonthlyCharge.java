package com.api.water_sytem_management_java.models;

import com.api.water_sytem_management_java.controllers.dtos.customer.CustomerStatus;
import com.api.water_sytem_management_java.models.customer.Customer;
import com.api.water_sytem_management_java.models.payment.Payment;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "tb_wsm_monthly_charges",

        uniqueConstraints = @UniqueConstraint(

                columnNames = {

                        "customer_id",

                        "reference_year",

                        "reference_month"
                }
        )
)
public class MonthlyCharge {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // =====================================================
    // CUSTOMER
    // =====================================================

    private final LocalDateTime createdAt =
            LocalDateTime.now();

    // =====================================================
    // REFERENCE DATE
    // =====================================================
    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(
            name = "customer_id",
            nullable = false
    )
    private Customer customer;
    /**
     * Ex:
     * 2026
     */
    @Column(
            name = "reference_year",
            nullable = false
    )
    private int referenceYear;

    // =====================================================
    // PAYMENT STATUS
    // =====================================================
    /**
     * Ex:
     * 5
     */
    @Column(
            name = "reference_month",
            nullable = false
    )
    private int referenceMonth;
    private boolean paid = false;
    private LocalDateTime paidAt;

    // =====================================================
    // SNAPSHOT DATA
    // =====================================================
    /**
     * Mês inicial do contrato.
     * <p>
     * Ex:
     * Cliente criado em Maio
     * Maio fica:
     * initialMonth = true
     * paid = true
     */
    private boolean initialMonth = false;
    /**
     * Valor da mensalidade naquele período.
     */
    private int amount;
    /**
     * Estado do cliente naquele período.
     */
    @Enumerated(EnumType.STRING)
    private CustomerStatus customerStatusSnapshot;
    /**
     * Número da válvula naquele período.
     */
    private byte valveSnapshot;
    /**
     * Nome histórico.
     */
    private String customerNameSnapshot;
    /**
     * Contacto histórico.
     */
    private String customerContactSnapshot;

    // =====================================================
    // PAYMENT RELATION
    // =====================================================
    /**
     * Endereço histórico.
     */
    private String customerAddressSnapshot;

    // =====================================================
    // AUDIT
    // =====================================================
    /**
     * Qual pagamento liquidou este mês.
     */
    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "payment_id")
    private Payment payment;

    // =====================================================
    // CONSTRUCTORS
    // =====================================================

    public MonthlyCharge() {
    }

    public MonthlyCharge(

            Customer customer,

            int referenceYear,

            int referenceMonth
    ) {

        this.customer = customer;

        this.referenceYear = referenceYear;

        this.referenceMonth = referenceMonth;

        this.paid = false;

        // ================================================
        // SNAPSHOTS
        // ================================================

        applyCustomerSnapshot(customer);
    }

    // =====================================================
    // BUSINESS LOGIC
    // =====================================================

    public void markAsPaid(
            Payment payment
    ) {

        this.paid = true;

        this.payment = payment;

        this.paidAt = LocalDateTime.now();
    }

    public void markAsUnpaid() {

        this.paid = false;

        this.payment = null;

        this.paidAt = null;
    }

    public void applyCustomerSnapshot(
            Customer customer
    ) {

        this.amount =
                customer.getMonthlyFee();

        this.customerStatusSnapshot =
                customer.getStatus();

        this.valveSnapshot =
                customer.getValve();

        this.customerNameSnapshot =
                customer.getName();

        this.customerContactSnapshot =
                customer.getContact();

        this.customerAddressSnapshot =
                customer.getAddress();
    }

    // =====================================================
    // HELPERS
    // =====================================================

    public String getFormattedReference() {

        return referenceYear +
                "-" +
                String.format(
                        "%02d",
                        referenceMonth
                );
    }

    // =====================================================
    // GETTERS & SETTERS
    // =====================================================

    public UUID getId() {
        return id;
    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(
            Customer customer
    ) {
        this.customer = customer;
    }

    public int getReferenceYear() {
        return referenceYear;
    }

    public void setReferenceYear(
            int referenceYear
    ) {
        this.referenceYear = referenceYear;
    }

    public int getReferenceMonth() {
        return referenceMonth;
    }

    public void setReferenceMonth(
            int referenceMonth
    ) {
        this.referenceMonth = referenceMonth;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(
            boolean paid
    ) {
        this.paid = paid;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(
            LocalDateTime paidAt
    ) {
        this.paidAt = paidAt;
    }

    public boolean isInitialMonth() {
        return initialMonth;
    }

    public void setInitialMonth(
            boolean initialMonth
    ) {
        this.initialMonth = initialMonth;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(
            int amount
    ) {
        this.amount = amount;
    }

    public CustomerStatus getCustomerStatusSnapshot() {
        return customerStatusSnapshot;
    }

    public void setCustomerStatusSnapshot(
            CustomerStatus customerStatusSnapshot
    ) {

        this.customerStatusSnapshot =
                customerStatusSnapshot;
    }

    public byte getValveSnapshot() {
        return valveSnapshot;
    }

    public void setValveSnapshot(
            byte valveSnapshot
    ) {
        this.valveSnapshot = valveSnapshot;
    }

    public String getCustomerNameSnapshot() {
        return customerNameSnapshot;
    }

    public void setCustomerNameSnapshot(
            String customerNameSnapshot
    ) {

        this.customerNameSnapshot =
                customerNameSnapshot;
    }

    public String getCustomerContactSnapshot() {
        return customerContactSnapshot;
    }

    public void setCustomerContactSnapshot(
            String customerContactSnapshot
    ) {

        this.customerContactSnapshot =
                customerContactSnapshot;
    }

    public String getCustomerAddressSnapshot() {
        return customerAddressSnapshot;
    }

    public void setCustomerAddressSnapshot(
            String customerAddressSnapshot
    ) {

        this.customerAddressSnapshot =
                customerAddressSnapshot;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(
            Payment payment
    ) {
        this.payment = payment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}