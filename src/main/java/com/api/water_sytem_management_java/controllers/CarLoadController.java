package com.api.water_sytem_management_java.controllers;

import com.api.water_sytem_management_java.controllers.dtos.CarLoadInput;
import com.api.water_sytem_management_java.controllers.dtos.CarLoadOutPut;
import com.api.water_sytem_management_java.models.CarLoad;
import com.api.water_sytem_management_java.models.Driver;
import com.api.water_sytem_management_java.models.Manager;
import com.api.water_sytem_management_java.models.Sprint;
import com.api.water_sytem_management_java.repositories.DriverRepository;
import com.api.water_sytem_management_java.repositories.ManagerRepository;
import com.api.water_sytem_management_java.repositories.SprintRepository;
import com.api.water_sytem_management_java.services.CarLoadService;
import com.api.water_sytem_management_java.services.ManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/carloads")
public class CarLoadController {

    private final CarLoadService carLoadService;
    private final ManagerRepository managerRepository;
    private final DriverRepository driverRepository;
    private final SprintRepository sprintRepository;


    public CarLoadController(CarLoadService carLoadService, ManagerRepository managerRepository, ManagerService managerService, DriverRepository driverRepository, SprintRepository sprintRepository) {
        this.carLoadService = carLoadService;
        this.managerRepository = managerRepository;

        this.driverRepository = driverRepository;
        this.sprintRepository = sprintRepository;
    }

    @PostMapping
    public ResponseEntity<CarLoad> createCarLoad(@RequestBody CarLoadInput carLoadInput) {
        Manager manager = managerRepository.findById(carLoadInput.logisticsManagerId())
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        Driver driver = driverRepository.findById(carLoadInput.assignedDriverId())
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        Sprint sprint = sprintRepository.findById(carLoadInput.carloadBatchId())
                .orElseThrow(() -> new RuntimeException("Sprint not found"));

        CarLoad carLoad = carLoadInput.toCarLoad(manager, driver, sprint); // Você deve ter este método no DTO
        CarLoad savedCarLoad = carLoadService.createCarLoad(carLoad);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCarLoad);
    }


    @GetMapping
    public ResponseEntity<List<CarLoadOutPut>> getAllCarLoads() {
        List<CarLoadOutPut> carLoads = carLoadService.getAllCarLoads();
        return ResponseEntity.ok(carLoads);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarLoadOutPut> getCarLoadById(@PathVariable UUID id) {
        CarLoadOutPut carLoad = carLoadService.getCarLoadById(id);
        return ResponseEntity.ok(carLoad);
    }

    @GetMapping("/sprint/{id}")
    public ResponseEntity<List<CarLoadOutPut>> getCarLoadBySprintId(@PathVariable UUID id) {
        List<CarLoadOutPut> carLoads = carLoadService.getCarloadbySprint(id);
        return ResponseEntity.ok(carLoads);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarLoad(@PathVariable UUID id) {
        carLoadService.deleteCarLoad(id);
        return ResponseEntity.noContent().build();
    }
}