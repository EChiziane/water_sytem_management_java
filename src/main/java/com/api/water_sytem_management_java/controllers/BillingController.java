package com.api.water_sytem_management_java.controllers;

import com.api.water_sytem_management_java.controllers.dtos.MonthlyChargeOutput;
import com.api.water_sytem_management_java.models.MonthlyCharge;
import com.api.water_sytem_management_java.services.MonthlyChargeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/billing")
public class BillingController {

    private final MonthlyChargeService service;

    public BillingController(MonthlyChargeService service) {
        this.service = service;
    }

    @GetMapping
    public List<MonthlyChargeOutput> getAll() {
        return service.getAll();
    }
    @PostMapping("/generate-month")
    public ResponseEntity<String> generateMonth() {
        service.generateMonthForAllCustomers();
        return ResponseEntity.ok("Mês gerado com sucesso");
    }
}