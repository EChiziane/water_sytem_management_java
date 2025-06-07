package com.api.water_sytem_management_java.services;


import com.api.water_sytem_management_java.controllers.dtos.CarLoadOutPut;
import com.api.water_sytem_management_java.models.CarLoad;
import com.api.water_sytem_management_java.repositories.CarLoadRepository;
import com.api.water_sytem_management_java.repositories.ManagerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CarLoadService {

    private final CarLoadRepository carLoadRepository;
    private final ManagerRepository managerRepository;

    @Autowired
    public CarLoadService(CarLoadRepository carLoadRepository, ManagerRepository managerRepository) {
        this.carLoadRepository = carLoadRepository;
        this.managerRepository = managerRepository;
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

    public List<CarLoadOutPut> getCarloadbySprint(UUID id) {
        return carLoadRepository.findByCarloadBatchSprintId(id)
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
