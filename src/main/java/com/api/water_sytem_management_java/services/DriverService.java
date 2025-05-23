package com.api.water_sytem_management_java.services;

import com.api.water_sytem_management_java.controllers.dtos.DriverInput;
import com.api.water_sytem_management_java.models.Driver;
import com.api.water_sytem_management_java.models.DriverOutPut;
import com.api.water_sytem_management_java.repositories.DriverRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DriverService {
    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Transactional
    public Driver createDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public List<DriverOutPut> getAllDrivers() {
        return driverRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(this::mapToDriverOutPut)
                .collect(Collectors.toList());

    }

    private DriverOutPut mapToDriverOutPut(Driver driver) {
        return driver.toDriverOutPut(driver);
    }

    public  void  deleteDriver(UUID driverId) {
        driverRepository.deleteById(driverId);
    }


    @Transactional
    public Optional<DriverOutPut> driverUpdate(@PathVariable UUID id, DriverInput input) {
        return driverRepository.findById(id)
                .map(existingDriver->{
                    existingDriver.setName(input.name());
                    existingDriver.setCarDescription(input.carDescription());
                    existingDriver.setStatus(input.status());
                    existingDriver.setPhone(input.phone());
                    Driver updatedDriver = driverRepository.save(existingDriver);
                    return mapToDriverOutPut(updatedDriver);

                });
    }
}
