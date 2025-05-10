package com.api.water_sytem_management_java.services;


import com.api.water_sytem_management_java.models.CarLoad;
import com.api.water_sytem_management_java.controllers.dtos.CarLoadInput;
import com.api.water_sytem_management_java.controllers.dtos.CarLoadOutPut;
import com.api.water_sytem_management_java.repositories.CarLoadRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CarLoadService {

    private final CarLoadRepository carLoadRepository;

    @Autowired
    public CarLoadService(CarLoadRepository carLoadRepository) {
        this.carLoadRepository = carLoadRepository;
    }

    @Transactional
    public CarLoad createCarLoad(CarLoad carLoad) {
        return carLoadRepository.save(carLoad);
    }

    public List<CarLoadOutPut> getAllCarLoads() {
        return carLoadRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(this::mapToCarLoadOutput)
                .collect(Collectors.toList());
    }

    public CarLoadOutPut getCarLoadById(UUID id) {
        return carLoadRepository.findById(id)
                .map(this::mapToCarLoadOutput)
                .orElseThrow(() -> new CarLoadNotFoundException("No car load found with ID: " + id));
    }

    public void deleteCarLoad(UUID id) {
        carLoadRepository.deleteById(id);
    }



    private CarLoadOutPut mapToCarLoadOutput(CarLoad carLoad) {

        return carLoad.toCarLoadOutPut();
    }
}
