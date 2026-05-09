package com.api.water_sytem_management_java.services;

import com.api.water_sytem_management_java.controllers.dtos.MonthlyChargeOutput;
import com.api.water_sytem_management_java.controllers.dtos.MonthlyChargeTimelineOutput;
import com.api.water_sytem_management_java.models.MonthlyCharge;
import com.api.water_sytem_management_java.models.customer.Customer;
import com.api.water_sytem_management_java.models.payment.Payment;
import com.api.water_sytem_management_java.repositories.MonthlyChargeRepository;
import com.api.water_sytem_management_java.repositories.customer.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@Service
public class MonthlyChargeService {

    private final MonthlyChargeRepository repository;

    private final CustomerRepository customerRepository;

    public MonthlyChargeService(
            MonthlyChargeRepository repository,
            CustomerRepository customerRepository
    ) {

        this.repository = repository;

        this.customerRepository = customerRepository;
    }

    // =====================================================
    // SYNC ALL CUSTOMERS
    // =====================================================

    @Transactional
    public void syncAllCustomersTimeline() {

        List<Customer> customers =
                customerRepository.findAll();

        for (Customer customer : customers) {

            syncCustomerTimeline(customer);
        }
    }

    // =====================================================
    // SYNC CUSTOMER TIMELINE
    // =====================================================

    @Transactional
    public void syncCustomerTimeline(
            Customer customer
    ) {

        if (customer == null) {
            return;
        }

        YearMonth createdMonth =
                YearMonth.from(
                        customer.getCreatedAt()
                );

        YearMonth currentMonth =
                YearMonth.now();

        YearMonth processingMonth =
                createdMonth;

        while (
                !processingMonth.isAfter(currentMonth)
        ) {

            int year =
                    processingMonth.getYear();

            int month =
                    processingMonth.getMonthValue();

            boolean exists =

                    repository

                            .findByCustomerIdAndReferenceYearAndReferenceMonth(

                                    customer.getId(),

                                    year,

                                    month
                            )

                            .isPresent();

            if (!exists) {

                MonthlyCharge monthlyCharge =

                        new MonthlyCharge(

                                customer,

                                year,

                                month
                        );

                monthlyCharge.applyCustomerSnapshot(
                        customer
                );

                repository.save(
                        monthlyCharge
                );
            }

            processingMonth =
                    processingMonth.plusMonths(1);
        }
    }

    // =====================================================
    // GET ALL
    // =====================================================

    public List<MonthlyChargeOutput> getAll() {

        return repository

                .findAll()

                .stream()

                .map(mc -> new MonthlyChargeOutput(

                        mc.getId(),

                        mc.getCustomerNameSnapshot(),

                        mc.getFormattedReference(),

                        mc.isPaid()
                ))

                .toList();
    }

    // =====================================================
    // GET CUSTOMER TIMELINE
    // =====================================================

    public List<MonthlyChargeTimelineOutput>
    getCustomerTimeline(UUID customerId) {

        return repository

                .findByCustomerIdOrderByReferenceYearAscReferenceMonthAsc(
                        customerId
                )

                .stream()

                .map(this::mapTimelineOutput)

                .toList();
    }

    // =====================================================
    // GET CUSTOMER UNPAID
    // =====================================================

    public List<MonthlyChargeTimelineOutput>
    getCustomerUnpaidMonths(UUID customerId) {

        return repository

                .findByCustomerIdAndPaidFalseOrderByReferenceYearAscReferenceMonthAsc(
                        customerId
                )

                .stream()

                .map(this::mapTimelineOutput)

                .toList();
    }

    // =====================================================
    // GET SPECIFIC MONTH
    // =====================================================

    public MonthlyChargeTimelineOutput
    getCustomerMonth(

            UUID customerId,

            int year,

            int month

    ) {

        MonthlyCharge monthlyCharge =

                repository

                        .findByCustomerIdAndReferenceYearAndReferenceMonth(

                                customerId,

                                year,

                                month
                        )

                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Monthly charge not found"
                                )
                        );

        return mapTimelineOutput(
                monthlyCharge
        );
    }

    // =====================================================
    // CALCULATE DEBT
    // =====================================================

    public long calculateDebtMonths(
            UUID customerId
    ) {

        return repository

                .countByCustomerIdAndPaidFalse(
                        customerId
                );
    }

    // =====================================================
    // MAP TIMELINE DTO
    // =====================================================

    private MonthlyChargeTimelineOutput
    mapTimelineOutput(
            MonthlyCharge mc
    ) {

        Payment payment =
                mc.getPayment();

        return new MonthlyChargeTimelineOutput(

                // =================================================
                // MONTHLY
                // =================================================

                mc.getId(),

                mc.getFormattedReference(),

                mc.isPaid(),

                mc.getPaidAt(),

                Double.valueOf(
                        mc.getAmount()
                ),

                // =================================================
                // SNAPSHOTS
                // =================================================

                mc.getCustomerNameSnapshot(),

                mc.getCustomerContactSnapshot(),

                mc.getCustomerAddressSnapshot(),

                String.valueOf(
                        mc.getValveSnapshot()
                ),

                mc.getCustomerStatusSnapshot() != null
                        ? mc.getCustomerStatusSnapshot().name()
                        : null,

                // =================================================
                // PAYMENT
                // =================================================

                payment != null
                        ? payment.getId()
                        : null,

                payment != null
                        ? payment.getReferenceCode()
                        : null,

                payment != null
                        ? payment.getPaymentMethod()
                        : null,

                payment != null &&
                        payment.getCreatedBy() != null
                        ? payment.getCreatedBy().getName()
                        : null,

                payment != null
                        ? payment.getCreatedAt()
                        : null
        );
    }

    public List<MonthlyCharge>
    getCustomerUnpaidEntities(UUID customerId) {

        return repository
                .findByCustomerIdAndPaidFalseOrderByReferenceYearAscReferenceMonthAsc(
                        customerId
                );
    }
}