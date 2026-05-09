package com.api.water_sytem_management_java.controllers;

import com.api.water_sytem_management_java.controllers.dtos.MonthlyChargeOutput;
import com.api.water_sytem_management_java.controllers.dtos.MonthlyChargeTimelineOutput;
import com.api.water_sytem_management_java.services.MonthlyChargeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/billing")
public class BillingController {

    private final MonthlyChargeService service;

    public BillingController(
            MonthlyChargeService service
    ) {

        this.service = service;
    }

    // =====================================================
    // GET ALL MONTHLY CHARGES
    // =====================================================

    @GetMapping
    public List<MonthlyChargeOutput> getAll() {

        return service.getAll();
    }

    // =====================================================
    // SYNC ALL TIMELINES
    // =====================================================

    @PostMapping("/sync")
    public ResponseEntity<String> syncAll() {

        service.syncAllCustomersTimeline();

        return ResponseEntity.ok(
                "Timeline sincronizada com sucesso"
        );
    }

    // =====================================================
    // GET CUSTOMER TIMELINE
    // =====================================================

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<MonthlyChargeTimelineOutput>>
    getCustomerTimeline(

            @PathVariable UUID customerId
    ) {

        List<MonthlyChargeTimelineOutput> timeline =

                service.getCustomerTimeline(
                        customerId
                );

        return ResponseEntity.ok(
                timeline
        );
    }

    // =====================================================
    // GET CUSTOMER UNPAID MONTHS
    // =====================================================

    @GetMapping("/customer/{customerId}/unpaid")
    public ResponseEntity<List<MonthlyChargeTimelineOutput>>
    getCustomerUnpaidMonths(

            @PathVariable UUID customerId
    ) {

        List<MonthlyChargeTimelineOutput> unpaidMonths =

                service.getCustomerUnpaidMonths(
                        customerId
                );

        return ResponseEntity.ok(
                unpaidMonths
        );
    }

    // =====================================================
    // GET SPECIFIC MONTH
    // =====================================================

    @GetMapping(
            "/customer/{customerId}/month/{year}/{month}"
    )
    public ResponseEntity<MonthlyChargeTimelineOutput>
    getCustomerMonth(

            @PathVariable UUID customerId,

            @PathVariable int year,

            @PathVariable int month
    ) {

        MonthlyChargeTimelineOutput monthlyCharge =

                service.getCustomerMonth(

                        customerId,

                        year,

                        month
                );

        return ResponseEntity.ok(
                monthlyCharge
        );
    }

    // =====================================================
    // GET CUSTOMER DEBT COUNT
    // =====================================================

    @GetMapping("/customer/{customerId}/debt-count")
    public ResponseEntity<Long>
    getCustomerDebtCount(

            @PathVariable UUID customerId
    ) {

        long debtCount =

                service.calculateDebtMonths(
                        customerId
                );

        return ResponseEntity.ok(
                debtCount
        );
    }

}