package com.api.water_sytem_management_java.controllers;

import com.api.water_sytem_management_java.controllers.dtos.CustomerInput;
import com.api.water_sytem_management_java.controllers.dtos.CustomerOutput;
import com.api.water_sytem_management_java.models.Customer;
import com.api.water_sytem_management_java.services.CustomerService;
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
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerInput customerInput) {
        Customer customer = customerInput.toCustomer();
        Customer savedCustomer = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }

    @GetMapping
    public ResponseEntity<List<CustomerOutput>> getAllCustomers() {
        List<CustomerOutput> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CustomerOutput>> getCustomerById(@PathVariable UUID id) {
        Optional<CustomerOutput> customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
