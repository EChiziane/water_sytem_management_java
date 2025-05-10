package com.api.water_sytem_management_java.controllers;


import com.api.water_sytem_management_java.controllers.dtos.CarLoadOutPut;
import com.api.water_sytem_management_java.controllers.dtos.CarloadInput;
import com.api.water_sytem_management_java.models.CarLoad;
import com.api.water_sytem_management_java.services.CarLoadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/carloads")
public class CarLoadController {
    private final CarLoadService carloadService;

    public CarLoadController(CarLoadService carloadService) {
        this.carloadService = carloadService;
    }

    @PostMapping
    public ResponseEntity<CarLoad> createCarload(@RequestBody CarloadInput carloadInput){
        CarLoad carLoad= carloadInput.toCarLoad();
        CarLoad savedCarload= carloadService.createCarload(carLoad);
        return  ResponseEntity.status(HttpStatus.CREATED).body(savedCarload);
    }

    @GetMapping
    public  ResponseEntity<List<CarLoadOutPut>> getAllCarloads(){
        List<CarLoadOutPut> carloads= carloadService.getAllCarloads();
        return  ResponseEntity.ok(carloads);

    }
    @GetMapping("/{id}")
    public ResponseEntity<CarLoadOutPut> getCarloadById(@PathVariable UUID id){
        CarLoadOutPut carload= carloadService.getCarloadById(id);
        return ResponseEntity.ok(carload);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteCarload(@PathVariable UUID id){
        carloadService.deleteCarload(id);
        return ResponseEntity.noContent().build();
    }

}
