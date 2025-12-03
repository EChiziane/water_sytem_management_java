package com.api.water_sytem_management_java.models;


import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tb_receipts")
public class Recibo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id; // ID único do recibo no banco

    private String nomeCliente;
    private String numeroCliente;
    private String enderecoCliente;

    private String numeroRecibo;

    private LocalDate dataPagamento;
    private LocalDate dataRecibo;

    private UUID idCliente; // FK para cliente

    private String descricaoProduto;
    private int quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal totalPagar;

    public Recibo() {
    }

    public Recibo(String nomeCliente, String numeroCliente, String enderecoCliente,
                  String numeroRecibo, LocalDate dataPagamento, LocalDate dataRecibo,
                  UUID idCliente, String descricaoProduto, int quantidade,
                  BigDecimal precoUnitario, BigDecimal totalPagar) {
        this.nomeCliente = nomeCliente;
        this.numeroCliente = numeroCliente;
        this.enderecoCliente = enderecoCliente;
        this.numeroRecibo = numeroRecibo;
        this.dataPagamento = dataPagamento;
        this.dataRecibo = dataRecibo;
        this.idCliente = idCliente;
        this.descricaoProduto = descricaoProduto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.totalPagar = totalPagar;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getNumeroCliente() {
        return numeroCliente;
    }

    public void setNumeroCliente(String numeroCliente) {
        this.numeroCliente = numeroCliente;
    }

    public String getEnderecoCliente() {
        return enderecoCliente;
    }

    public void setEnderecoCliente(String enderecoCliente) {
        this.enderecoCliente = enderecoCliente;
    }

    public String getNumeroRecibo() {
        return numeroRecibo;
    }

    public void setNumeroRecibo(String numeroRecibo) {
        this.numeroRecibo = numeroRecibo;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public LocalDate getDataRecibo() {
        return dataRecibo;
    }

    public void setDataRecibo(LocalDate dataRecibo) {
        this.dataRecibo = dataRecibo;
    }

    public UUID getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(UUID idCliente) {
        this.idCliente = idCliente;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public BigDecimal getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(BigDecimal totalPagar) {
        this.totalPagar = totalPagar;
    }
}
