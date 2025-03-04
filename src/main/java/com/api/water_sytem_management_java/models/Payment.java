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
@Table(name = "tb_payment12")
public class Payment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private Double amount;
    private String referenceMonth;
    private byte numMonths;
    private String paymentMethod;
    private Boolean confirmed;
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
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
                payment.createdAt
        );
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

    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}