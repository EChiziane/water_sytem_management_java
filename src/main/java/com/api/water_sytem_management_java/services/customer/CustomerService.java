package com.api.water_sytem_management_java.services.customer;

import com.api.water_sytem_management_java.CustomerNotFoundException;
import com.api.water_sytem_management_java.controllers.dtos.customer.CustomerInput;
import com.api.water_sytem_management_java.controllers.dtos.customer.CustomerOutput;
import com.api.water_sytem_management_java.controllers.dtos.customer.CustomerStatus;
import com.api.water_sytem_management_java.models.customer.Customer;
import com.api.water_sytem_management_java.repositories.customer.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @Transactional
    public Customer createNewCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<CustomerOutput> fetchAllCustomers() {
        return customerRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream()
                .map(this::convertToCustomerOutput)
                .collect(Collectors.toList());
    }

    public CustomerOutput fetchCustomerById(UUID id) {
        return customerRepository.findById(id)
                .map(this::convertToCustomerOutput)
                .orElseThrow(() -> new CustomerNotFoundException("No user found with ID: " + id));
    }

    public void removeCustomerById(UUID id) {
        customerRepository.deleteById(id);
    }

    @Transactional
    public Optional<CustomerOutput> updateExistingCustomer(UUID id, CustomerInput input) {
        return customerRepository.findById(id)
                .map(existingCustomer -> {
                    existingCustomer.setName(input.name());
                    existingCustomer.setContact(input.contact());
                    existingCustomer.setAddress(input.address());
                    existingCustomer.setStatus(input.status());
                    existingCustomer.setValve(input.valve());
                    existingCustomer.setMonthsInDebt(input.monthsInDebt());
                    Customer updated = customerRepository.save(existingCustomer);
                    return convertToCustomerOutput(updated);
                });
    }

    private CustomerOutput convertToCustomerOutput(Customer customer) {
        boolean isActive = customer.getStatus() != null && customer.getStatus().equals(CustomerStatus.ATIVO);
        return customer.CustomerOutput(customer);
    }
}