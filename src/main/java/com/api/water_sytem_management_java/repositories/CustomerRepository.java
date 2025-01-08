package com.api.water_sytem_management_java.repositories;



import com.api.water_sytem_management_java.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CustomerRepository extends JpaRepository<Customer,UUID> {
}
