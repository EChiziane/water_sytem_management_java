package com.api.water_sytem_management_java.controllers.dtos;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record CustomerOutput(UUID id,
                             String name,
                             String contact,
                             String address,
                             Boolean active,
                             Integer valve,
                             Integer monthsInDebt,
                             String referenceMonth) {

    public CustomerOutput(UUID id,
                          String name,
                          String contact,
                          String address,
                          Boolean active,
                          Integer valve,
                          Integer monthsInDebt,
                          String referenceMonth) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.active = active;
        this.valve = valve;
        this.monthsInDebt = monthsInDebt;
        this.referenceMonth= getReferenceMonth(monthsInDebt);
    }


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
