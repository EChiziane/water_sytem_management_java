package com.api.water_sytem_management_java.repositories;



import com.api.water_sytem_management_java.models.MonthlyCharge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MonthlyChargeRepository extends JpaRepository<MonthlyCharge, UUID> {

    List<MonthlyCharge> findByCustomerIdAndPaidFalseOrderByReferenceMonthAsc(UUID customerId);

    Optional<MonthlyCharge> findByCustomerIdAndReferenceMonth(UUID customerId, String referenceMonth);

    long countByCustomerIdAndPaidFalse(UUID customerId);
}