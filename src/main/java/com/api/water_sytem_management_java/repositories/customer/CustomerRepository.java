package com.api.water_sytem_management_java.repositories.customer;

import com.api.water_sytem_management_java.models.customer.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    List<Customer> findByCodeStartingWithOrderByCodeDesc(String prefix, Pageable pageable);
}