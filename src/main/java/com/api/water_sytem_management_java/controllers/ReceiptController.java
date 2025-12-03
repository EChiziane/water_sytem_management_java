package com.api.water_sytem_management_java.controllers;

import com.api.water_sytem_management_java.controllers.dtos.ReceiptInput;
import com.api.water_sytem_management_java.controllers.dtos.ReceiptOutput;
import com.api.water_sytem_management_java.models.Recibo;
import com.api.water_sytem_management_java.services.ReceiptService;
import com.api.water_sytem_management_java.services.ReciboService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;
    private final ReciboService reciboService;

    public ReceiptController(ReceiptService receiptService, ReciboService reciboService) {
        this.receiptService = receiptService;
        this.reciboService = reciboService;
    }

    @PostMapping
    public ResponseEntity<Recibo> createReceipt(@RequestBody ReceiptInput receiptInput) throws IOException {
      //  Recibo receipt = receiptInput.toRecibo();
       File recibo=reciboService.atualizarRecibo("receipt.getNomeCliente()","receipt.getEnderecoCliente()","10/01/01");
     reciboService.imprimir(recibo);
      //  Recibo savedReceipt = receiptService.createReceipt(receipt);
      //  return ResponseEntity.status(HttpStatus.CREATED).body(savedReceipt);
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReceiptOutput> updateReceipt(@PathVariable UUID id, @RequestBody ReceiptInput receiptInput) {
        Optional<ReceiptOutput> updatedReceipt = receiptService.updateReceipt(id, receiptInput);
        return updatedReceipt
                .map(receipt -> ResponseEntity.ok().body(receipt))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ReceiptOutput>> getAllReceipts() {
        List<ReceiptOutput> receipts = receiptService.getAllReceipts();
        return ResponseEntity.ok(receipts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceiptOutput> getReceiptById(@PathVariable UUID id) throws ChangeSetPersister.NotFoundException {
        ReceiptOutput receipt = receiptService.getReceiptById(id);
        return ResponseEntity.ok(receipt);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReceipt(@PathVariable UUID id) {
        receiptService.deleteReceipt(id);
        return ResponseEntity.noContent().build();
    }
}
