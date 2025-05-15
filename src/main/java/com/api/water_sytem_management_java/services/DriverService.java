package com.api.water_sytem_management_java.services;

import com.api.water_sytem_management_java.models.Driver;
import com.api.water_sytem_management_java.models.DriverOutPut;
import com.api.water_sytem_management_java.repositories.DriverRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
