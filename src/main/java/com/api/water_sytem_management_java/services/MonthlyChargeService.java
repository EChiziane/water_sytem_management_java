package com.api.water_sytem_management_java.services;

import com.api.water_sytem_management_java.controllers.dtos.MonthlyChargeOutput;
import com.api.water_sytem_management_java.models.MonthlyCharge;
import com.api.water_sytem_management_java.models.customer.Customer;
import com.api.water_sytem_management_java.repositories.MonthlyChargeRepository;
import com.api.water_sytem_management_java.repositories.customer.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;

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

    @Transactional
    public void generateMonthForAllCustomers() {

        String currentMonth =
                YearMonth.now().toString();

        List<Customer> customers =
                customerRepository.findAll();

        for (Customer customer : customers) {

            boolean exists =
                    repository
                            .findByCustomerIdAndReferenceMonth(
                                    customer.getId(),
                                    currentMonth
                            )
                            .isPresent();

            if (!exists) {

                repository.save(
                        new MonthlyCharge(
                                customer,
                                currentMonth
                        )
                );
            }
        }
    }

    public List<MonthlyChargeOutput> getAll() {

        return repository.findAll()
                .stream()
                .map(mc -> new MonthlyChargeOutput(
                        mc.getId(),
                        mc.getCustomer().getName(),
                        mc.getReferenceMonth(),
                        mc.isPaid()
                ))
                .toList();
    }
}