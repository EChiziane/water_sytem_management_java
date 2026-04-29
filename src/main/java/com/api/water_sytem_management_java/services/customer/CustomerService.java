package com.api.water_sytem_management_java.services.customer;

import com.api.water_sytem_management_java.CustomerNotFoundException;
import com.api.water_sytem_management_java.controllers.dtos.customer.CustomerInput;
import com.api.water_sytem_management_java.controllers.dtos.customer.CustomerOutput;
import com.api.water_sytem_management_java.models.customer.Customer;
import com.api.water_sytem_management_java.repositories.customer.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer createNewCustomer(Customer customer) {
        customer.setCode(generateCustomerCode());
        return customerRepository.save(customer);
    }

    public List<CustomerOutput> fetchAllCustomers() {
        return customerRepository.findAll().stream()
                .map(Customer::toCustomerOutput)
                .toList();
    }

    public CustomerOutput fetchCustomerById(UUID id) {
        return customerRepository.findById(id)
                .map(Customer::toCustomerOutput)
                .orElseThrow(() -> new CustomerNotFoundException("No user found with ID: " + id));
    }

    public void removeCustomerById(UUID id) {
        customerRepository.deleteById(id);
    }

    @Transactional
    public Optional<CustomerOutput> updateExistingCustomer(UUID id, CustomerInput input) {
        return customerRepository.findById(id)
                .map(existing -> {
                    existing.setName(input.name());
                    existing.setContact(input.contact());
                    existing.setAddress(input.address());
                    existing.setStatus(input.status());
                    existing.setValve(input.valve());
                    existing.setMonthsInDebt(input.monthsInDebt());
                    existing.setMonthlyFee(input.monthlyFee());
                    return customerRepository.save(existing).toCustomerOutput();
                });
    }

    private String generateCustomerCode() {
        int year = LocalDate.now().getYear();
        String prefix = "WSM-" + year + "-";

        List<Customer> lastCustomers =
                customerRepository.findByCodeStartingWithOrderByCodeDesc(
                        prefix,
                        PageRequest.of(0, 1)
                );

        int next = 1;

        if (!lastCustomers.isEmpty()) {
            String lastCode = lastCustomers.get(0).getCode();
            String numberPart = lastCode.substring(prefix.length());
            next = Integer.parseInt(numberPart) + 1;
        }

        return prefix + String.format("%04d", next);
    }
}