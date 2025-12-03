package com.api.water_sytem_management_java.services;

import com.api.water_sytem_management_java.controllers.dtos.ReceiptInput;
import com.api.water_sytem_management_java.controllers.dtos.ReceiptOutput;
import com.api.water_sytem_management_java.models.Recibo;
import com.api.water_sytem_management_java.repositories.ReceiptRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReceiptService {

    private final ReceiptRepository receiptRepository;

    @Autowired
    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    @Transactional
    public Recibo createReceipt(Recibo receipt) {
        return receiptRepository.save(receipt);
    }

    public List<ReceiptOutput> getAllReceipts() {
        return receiptRepository.findAll(Sort.by(Sort.Direction.DESC, "dataRecibo")).stream()
                .map(this::mapToReceiptOutput)
                .collect(Collectors.toList());
    }

    public ReceiptOutput getReceiptById(UUID id) throws ChangeSetPersister.NotFoundException {
        return receiptRepository.findById(id)
                .map(this::mapToReceiptOutput)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }

    public void deleteReceipt(UUID id) {
        receiptRepository.deleteById(id);
    }

    @Transactional
    public Optional<ReceiptOutput> updateReceipt(UUID id, ReceiptInput input) {
        return receiptRepository.findById(id)
                .map(existingReceipt -> {
                    existingReceipt.setNomeCliente(input.nomeCliente());
                    existingReceipt.setNumeroCliente(input.numeroCliente());
                    existingReceipt.setEnderecoCliente(input.enderecoCliente());
                    existingReceipt.setNumeroRecibo(input.numeroRecibo());
                    existingReceipt.setDataPagamento(input.dataPagamento());
                    existingReceipt.setDataRecibo(input.dataRecibo());
                    existingReceipt.setIdCliente(input.idCliente());
                    existingReceipt.setDescricaoProduto(input.descricaoProduto());
                    existingReceipt.setQuantidade(input.quantidade());
                    existingReceipt.setPrecoUnitario(input.precoUnitario());
                    existingReceipt.setTotalPagar(input.totalPagar());

                    Recibo updated = receiptRepository.save(existingReceipt);
                    return mapToReceiptOutput(updated);
                });
    }

    private ReceiptOutput mapToReceiptOutput(Recibo receipt) {
        return new ReceiptOutput(
                receipt.getId(),
                receipt.getNomeCliente(),
                receipt.getNumeroCliente(),
                receipt.getEnderecoCliente(),
                receipt.getNumeroRecibo(),
                receipt.getDataPagamento(),
                receipt.getDataRecibo(),
                receipt.getIdCliente(),
                receipt.getDescricaoProduto(),
                receipt.getQuantidade(),
                receipt.getPrecoUnitario(),
                receipt.getTotalPagar()
        );
    }
}
