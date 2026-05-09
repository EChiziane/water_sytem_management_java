package com.api.water_sytem_management_java.services.customer;

import com.api.water_sytem_management_java.CustomerNotFoundException;
import com.api.water_sytem_management_java.controllers.dtos.customer.CustomerInput;
import com.api.water_sytem_management_java.controllers.dtos.customer.CustomerOutput;
import com.api.water_sytem_management_java.models.MonthlyCharge;
import com.api.water_sytem_management_java.models.customer.Customer;
import com.api.water_sytem_management_java.repositories.MonthlyChargeRepository;
import com.api.water_sytem_management_java.repositories.customer.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final MonthlyChargeRepository monthlyChargeRepository;

    public CustomerService(
            CustomerRepository customerRepository,
            MonthlyChargeRepository monthlyChargeRepository
    ) {

        this.customerRepository =
                customerRepository;

        this.monthlyChargeRepository =
                monthlyChargeRepository;
    }

    // =====================================================
    // CREATE CUSTOMER
    // =====================================================

    @Transactional
    public Customer createNewCustomer(
            Customer customer,
            byte initialDebtMonths
    ) {

        // ================================================
        // GENERATE CODE
        // ================================================

        if (
                customer.getCode() == null
                        || customer.getCode().isBlank()
        ) {

            customer.setCode(
                    generateCustomerCode()
            );
        }

        // ================================================
        // SAVE CUSTOMER
        // ================================================

        Customer savedCustomer =
                customerRepository.save(customer);

        // ================================================
        // GENERATE INITIAL TIMELINE
        // ================================================

        generateInitialDebt(
                savedCustomer,
                initialDebtMonths
        );

        return savedCustomer;
    }

    // =====================================================
    // FETCH ALL CUSTOMERS
    // =====================================================

    public List<CustomerOutput> fetchAllCustomers() {

        return customerRepository.findAll()

                .stream()

                .map(this::mapToOutput)

                .toList();
    }

    // =====================================================
    // FETCH CUSTOMER BY ID
    // =====================================================

    public CustomerOutput fetchCustomerById(
            UUID id
    ) {

        return customerRepository.findById(id)

                .map(this::mapToOutput)

                .orElseThrow(() ->

                        new CustomerNotFoundException(
                                "No user found with ID: " + id
                        )
                );
    }

    // =====================================================
    // DELETE CUSTOMER
    // =====================================================

    public void removeCustomerById(
            UUID id
    ) {

        customerRepository.deleteById(id);
    }

    // =====================================================
    // UPDATE CUSTOMER
    // =====================================================

    @Transactional
    public Optional<CustomerOutput> updateExistingCustomer(
            UUID id,
            CustomerInput input
    ) {

        return customerRepository.findById(id)

                .map(existing -> {

                    existing.setName(
                            input.name()
                    );

                    existing.setContact(
                            input.contact()
                    );

                    existing.setAddress(
                            input.address()
                    );

                    existing.setStatus(
                            input.status()
                    );

                    existing.setValve(
                            input.valve()
                    );

                    existing.setMonthlyFee(
                            input.monthlyFee()
                    );

                    // ====================================
                    // ENSURE CODE
                    // ====================================

                    if (
                            existing.getCode() == null
                                    || existing.getCode().isBlank()
                    ) {

                        existing.setCode(
                                generateCustomerCode()
                        );
                    }

                    Customer updated =
                            customerRepository.save(existing);

                    return mapToOutput(updated);
                });
    }

    // =====================================================
    // MAP CUSTOMER OUTPUT
    // =====================================================

    private CustomerOutput mapToOutput(
            Customer customer
    ) {

        List<MonthlyCharge> unpaidCharges =

                monthlyChargeRepository

                        .findByCustomerIdAndPaidFalseOrderByReferenceYearAscReferenceMonthAsc(
                                customer.getId()
                        );

        List<String> unpaidMonths =

                unpaidCharges.stream()

                        .map(mc ->

                                mc.getReferenceYear()
                                        + "-"
                                        + String.format(
                                        "%02d",
                                        mc.getReferenceMonth()
                                )
                        )

                        .toList();

        return customer.toCustomerOutput(

                unpaidMonths.size(),

                unpaidMonths
        );
    }

    // =====================================================
    // GENERATE INITIAL TIMELINE / DEBT
    // =====================================================

    private void generateInitialDebt(
            Customer customer,
            byte monthsInDebt
    ) {

        // ================================================
        // DEFAULT
        // ================================================

        if (monthsInDebt <= 0) {

            monthsInDebt = 1;
        }

        YearMonth current =
                YearMonth.now();

        // ================================================
        // CREATE TIMELINE
        // ================================================

        for (
                int i = monthsInDebt - 1;
                i >= 0;
                i--
        ) {

            YearMonth targetMonth =
                    current.minusMonths(i);

            int year =
                    targetMonth.getYear();

            int month =
                    targetMonth.getMonthValue();

            // ============================================
            // CHECK IF EXISTS
            // ============================================

            boolean exists =

                    monthlyChargeRepository

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

                // ========================================
                // ALL START AS UNPAID
                // ========================================

                monthlyCharge.setPaid(false);

                // ========================================
                // FIRST MONTH = INITIAL MONTH
                // ========================================

                monthlyCharge.setInitialMonth(
                        i == monthsInDebt - 1
                );

                // ========================================
                // SNAPSHOT
                // ========================================

                monthlyCharge.applyCustomerSnapshot(
                        customer
                );

                monthlyChargeRepository.save(
                        monthlyCharge
                );
            }
        }
    }

    // =====================================================
    // GENERATE CUSTOMER CODE
    // =====================================================

    private String generateCustomerCode() {

        int year =
                LocalDate.now().getYear();

        String prefix =
                "WSM-" + year + "-";

        List<Customer> lastCustomers =

                customerRepository

                        .findByCodeStartingWithOrderByCodeDesc(

                                prefix,

                                PageRequest.of(0, 1)
                        );

        int next = 1;

        if (!lastCustomers.isEmpty()) {

            String lastCode =
                    lastCustomers.get(0).getCode();

            String numberPart =
                    lastCode.substring(
                            prefix.length()
                    );

            next =
                    Integer.parseInt(numberPart) + 1;
        }

        return prefix +
                String.format("%04d", next);
    }
}