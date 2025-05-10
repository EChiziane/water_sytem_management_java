package com.api.water_sytem_management_java.services;

import com.api.water_sytem_management_java.CustomerNotFoundException;
import com.api.water_sytem_management_java.controllers.dtos.CarLoadOutPut;
import com.api.water_sytem_management_java.models.CarLoad;
import com.api.water_sytem_management_java.repositories.CarloadRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class CarLoadService {
    private  final CarloadRepository carloadRepository;

    public CarLoadService(CarloadRepository carloadRepository) {
        this.carloadRepository = carloadRepository;
    }
@Transactional
    public CarLoad createCarload(CarLoad carLoad) {

        return carloadRepository.save(carLoad);
    }

    public List<CarLoadOutPut> getAllCarloads() {

        return carloadRepository.findAll(Sort.by(Sort.Direction.DESC,"createdAt"))
        .stream()
                .map(this::mapToCarloadOutPut)
                .collect(Collectors.toList());
    }



    public CarLoadOutPut getCarloadById(UUID id) {
        return carloadRepository.findById(id)
                .map(this::mapToCarloadOutPut)
                .orElseThrow(() -> new CustomerNotFoundException("No user found with ID: " + id));
    }

    public void deleteCarload(UUID id) {
        carloadRepository.deleteById(id);
    }
    private CarLoadOutPut mapToCarloadOutPut(CarLoad carLoad){
        return  carLoad.toCarLoadOutPut();
    }
}
