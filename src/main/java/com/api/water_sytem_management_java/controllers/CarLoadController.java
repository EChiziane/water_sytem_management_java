package com.api.water_sytem_management_java.controllers;

import com.api.water_sytem_management_java.models.CarLoad;
import com.api.water_sytem_management_java.controllers.dtos.CarLoadInput;
import com.api.water_sytem_management_java.controllers.dtos.CarLoadOutPut;
import com.api.water_sytem_management_java.services.CarLoadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/carloads")
public class CarLoadController {

    private final CarLoadService carLoadService;

    public CarLoadController(CarLoadService carLoadService) {
        this.carLoadService = carLoadService;
    }

    @PostMapping
    public ResponseEntity<CarLoad> createCarLoad(@RequestBody CarLoadInput carLoadInput) {
        CarLoad carLoad = carLoadInput.toCarLoad(); // Você deve ter este método no DTO
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarLoad(@PathVariable UUID id) {
        carLoadService.deleteCarLoad(id);
        return ResponseEntity.noContent().build();
    }
}