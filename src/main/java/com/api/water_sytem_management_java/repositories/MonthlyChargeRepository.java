package com.api.water_sytem_management_java.repositories;

import com.api.water_sytem_management_java.models.MonthlyCharge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MonthlyChargeRepository
        extends JpaRepository<MonthlyCharge, UUID> {

    // =====================================================
    // UNPAID MONTHS
    // =====================================================

    List<MonthlyCharge>
    findByCustomerIdAndPaidFalseOrderByReferenceYearAscReferenceMonthAsc(
            UUID customerId
    );

    // =====================================================
    // FIND SPECIFIC MONTH
    // =====================================================

    Optional<MonthlyCharge>
    findByCustomerIdAndReferenceYearAndReferenceMonth(

            UUID customerId,

            int referenceYear,

            int referenceMonth
    );

    // =====================================================
    // COUNT DEBT
    // =====================================================

    long countByCustomerIdAndPaidFalse(
            UUID customerId
    );

    // =====================================================
    // FULL TIMELINE
    // =====================================================

    List<MonthlyCharge>
    findByCustomerIdOrderByReferenceYearAscReferenceMonthAsc(
            UUID customerId
    );

    // =====================================================
    // PAID MONTHS
    // =====================================================

    List<MonthlyCharge>
    findByCustomerIdAndPaidTrueOrderByReferenceYearAscReferenceMonthAsc(
            UUID customerId
    );

    // =====================================================
    // FIND BY PAYMENT
    // =====================================================

    List<MonthlyCharge>
    findByPaymentIdOrderByReferenceYearAscReferenceMonthAsc(
            UUID paymentId
    );

    // =====================================================
    // CHECK MONTH EXISTS
    // =====================================================

    boolean existsByCustomerIdAndReferenceYearAndReferenceMonth(

            UUID customerId,

            int referenceYear,

            int referenceMonth
    );
}