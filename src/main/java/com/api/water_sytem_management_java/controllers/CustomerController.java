package com.api.water_sytem_management_java.controllers;

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
    public CustomerController(CustomerService customerService){this.customerService= customerService;}

    @PostMapping
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer){

        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.saveCostumer(customer));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> GetCustomers(){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Customer>> GetCustomerById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomerById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DeleteCostumer(@PathVariable UUID id){
        customerService.deleteCustomer(id);
        return  ResponseEntity.noContent().build();
    }
}
