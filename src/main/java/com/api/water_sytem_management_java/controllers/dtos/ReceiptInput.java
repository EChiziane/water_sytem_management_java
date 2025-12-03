package com.api.water_sytem_management_java.controllers.dtos;

import com.api.water_sytem_management_java.models.Recibo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ReceiptInput(
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
        BigDecimal totalPagar
) {
    public Recibo toRecibo() {
        return new Recibo(
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
                totalPagar
        );
    }
}
