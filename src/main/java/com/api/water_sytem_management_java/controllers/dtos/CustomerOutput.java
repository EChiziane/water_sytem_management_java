package com.api.water_sytem_management_java.controllers.dtos;

import com.api.water_sytem_management_java.models.CustomerStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record CustomerOutput(UUID id,
                             String name,
                             String contact,
                             String address,
                             CustomerStatus status,
                             byte valve,
                             byte monthsInDebt,
                             String referenceMonth,
                            LocalDateTime createdAt) {

    public CustomerOutput(UUID id,
                          String name,
                          String contact,
                          String address,
                          CustomerStatus  status,
                          byte valve,
                          byte monthsInDebt,
                          String referenceMonth,
                          LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.status = status;
        this.valve = valve;
        this.monthsInDebt = monthsInDebt;
        this.createdAt = createdAt;
        this.referenceMonth= getReferenceMonth(monthsInDebt);
    }


    public static String getReferenceMonth(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("O número de meses deve ser maior que zero.");
        }

        LocalDate today = LocalDate.now();

        return IntStream.range(0, n)
                .mapToObj(i -> today.minusMonths(n - 1 - i))
                .map(date -> date.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "PT")))
                .collect(Collectors.joining(", "));
    }
}
