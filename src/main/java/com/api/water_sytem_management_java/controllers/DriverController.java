package com.api.water_sytem_management_java.controllers;


import com.api.water_sytem_management_java.controllers.dtos.DriverInput;
import com.api.water_sytem_management_java.models.Driver;
import com.api.water_sytem_management_java.models.DriverOutPut;
import com.api.water_sytem_management_java.services.DriverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/drivers")
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    public ResponseEntity<Driver> createDriver(@RequestBody DriverInput driverInput) {
        Driver driver = driverInput.toDriver();
        Driver savedDriver = driverService.createDriver(driver);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDriver);
    }

    @GetMapping
    public ResponseEntity<List<DriverOutPut>> getAllDrivers() {

        List<DriverOutPut> drivers = driverService.getAllDrivers();
        return ResponseEntity.status(HttpStatus.OK).body(drivers);
    }
}
