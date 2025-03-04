package com.api.water_sytem_management_java.services;

import com.api.water_sytem_management_java.models.Customer;
import com.api.water_sytem_management_java.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getCustomers() {
        // Aqui você pode ordenar por createdAt em ordem decrescente
        return customerRepository.findAllByOrderByCreatedAtDesc();
    }

    public Optional<Customer> getCustomerById(UUID id) {
        return customerRepository.findById(id);
    }

    public void deleteCustomer(UUID id) {
        customerRepository.deleteById(id);
    }
}
