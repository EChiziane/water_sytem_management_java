package com.api.water_sytem_management_java.controllers.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public record ReceiptOutput(
        UUID id,
        String nomeCliente,
        String numeroCliente,
        String enderecoCliente,
        String numeroRecibo,
        LocalDate dataPagamento,
        LocalDate dataRecibo,
        UUID idCliente,
        String descricaoProduto,
        int quantidade,
        BigDecimal precoUnitario,
        BigDecimal totalPagar,
        String dataPagamentoFormatada,
        String dataReciboFormatada
) {

    public ReceiptOutput(UUID id,
                         String nomeCliente,
                         String numeroCliente,
                         String enderecoCliente,
                         String numeroRecibo,
                         LocalDate dataPagamento,
                         LocalDate dataRecibo,
                         UUID idCliente,
                         String descricaoProduto,
                         int quantidade,
                         BigDecimal precoUnitario,
                         BigDecimal totalPagar) {
        this(
                id,
                nomeCliente,
                numeroCliente,
                enderecoCliente,
                numeroRecibo,
                dataPagamento,
                dataRecibo,
                idCliente,
                descricaoProduto,
                quantidade,
                precoUnitario,
                totalPagar,
                formatarData(dataPagamento),
                formatarData(dataRecibo)
        );
    }

    private static String formatarData(LocalDate data) {
        return data != null ? data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
    }
}
