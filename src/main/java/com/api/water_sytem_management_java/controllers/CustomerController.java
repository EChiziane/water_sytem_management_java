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
    public ResponseEntity<CustomerOutput> createNewCustomer(
            @RequestBody CustomerInput input
    ) {

        Customer saved =
                customerService.createNewCustomer(
                        input.toCustomer(),
                        input.monthsInDebt()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        customerService.fetchCustomerById(
                                saved.getId()
                        )
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerOutput> updateExistingCustomer(
            @PathVariable UUID id,
            @RequestBody CustomerInput customerInput
    ) {

        Optional<CustomerOutput> updatedCustomer =
                customerService.updateExistingCustomer(
                        id,
                        customerInput
                );

        return updatedCustomer
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }

    @GetMapping
    public ResponseEntity<List<CustomerOutput>>
    fetchAllCustomers() {

        return ResponseEntity.ok(
                customerService.fetchAllCustomers()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerOutput>
    fetchCustomerById(@PathVariable UUID id) {

        return ResponseEntity.ok(
                customerService.fetchCustomerById(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    removeCustomerById(@PathVariable UUID id) {

        customerService.removeCustomerById(id);

        return ResponseEntity.noContent().build();
    }
}