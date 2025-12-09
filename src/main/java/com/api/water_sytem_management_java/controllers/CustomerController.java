package com.api.water_sytem_management_java.controllers;

import com.api.water_sytem_management_java.controllers.dtos.customer.CustomerInput;
import com.api.water_sytem_management_java.controllers.dtos.customer.CustomerOutput;
import com.api.water_sytem_management_java.models.customer.Customer;
import com.api.water_sytem_management_java.services.customer.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Customer> createNewCustomer(@RequestBody CustomerInput customerInput) {
        Customer customer = customerInput.toCustomer();
        Customer savedCustomer = customerService.createNewCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerOutput> updateExistingCustomer(@PathVariable UUID id, @RequestBody CustomerInput customerInput) {
        Optional<CustomerOutput> updatedCustomer = customerService.updateExistingCustomer(id, customerInput);
        return updatedCustomer
                .map(customer -> ResponseEntity.ok().body(customer))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CustomerOutput>> fetchAllCustomers() {
        List<CustomerOutput> customers = customerService.fetchAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerOutput> fetchCustomerById(@PathVariable UUID id) {
        CustomerOutput customer = customerService.fetchCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCustomerById(@PathVariable UUID id) {
        customerService.removeCustomerById(id);
        return ResponseEntity.noContent().build();
    }
}
