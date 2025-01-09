package com.api.water_sytem_management_java.controllers;

import com.api.water_sytem_management_java.models.Customer;
import com.api.water_sytem_management_java.models.Payment;
import com.api.water_sytem_management_java.repositories.CustomerRepository;
import com.api.water_sytem_management_java.services.PaymentService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final CustomerRepository customerRepository;

    public PaymentController(PaymentService paymentService, CustomerRepository customerRepository) {
        this.paymentService = paymentService;
        this.customerRepository = customerRepository;
    }

    @PostMapping
    public ResponseEntity<Payment> savePayment(@RequestBody paymentDto paymentDto) {

        // Busca o cliente associado ao pagamento pelo ID
        Customer customer = customerRepository.findById(paymentDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Verifica se o cliente possui dívidas
        if (customer.getMesesEmDivida() < 1) {
            throw new RuntimeException("O cliente não possui dívidas a serem pagas");
        }

        // Verifica se o número de meses a pagar é maior que os meses em dívida do cliente
        if (paymentDto.getNumMonths() > customer.getMesesEmDivida()) {
            throw new IllegalArgumentException("O número de meses a pagar é maior que o número de meses em dívida");
        }

        // Criação e configuração do pagamento
        Payment payment = new Payment();
        BeanUtils.copyProperties(paymentDto, payment);

        // Calcula e define os meses de referência do pagamento
        payment.setReferenceMonth(paymentService.getMonthsToPay(paymentDto.getNumMonths(), customer.getMesesEmDivida()));

        // Atualiza o número de meses em dívida do cliente
        customer.setMesesEmDivida(customer.getMesesEmDivida() - paymentDto.getNumMonths());

        // Associa o cliente ao pagamento
        payment.setCustomer(customer);

        // Salva o pagamento e retorna uma resposta com status CREATED
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.savePayment(payment));
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getPayments() {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getPayments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Payment>> getPaymentById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getPaymentById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable UUID id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
