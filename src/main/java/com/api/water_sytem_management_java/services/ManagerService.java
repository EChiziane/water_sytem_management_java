package com.api.water_sytem_management_java.services;

import com.api.water_sytem_management_java.controllers.dtos.ManagerOutPut;
import com.api.water_sytem_management_java.models.Manager;
import com.api.water_sytem_management_java.repositories.ManagerRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerService {

private  final ManagerRepository managerRepository;

    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }


    public List<ManagerOutPut> getAllManagers() {
        return  managerRepository.findAll(Sort.by(Sort.Direction.DESC,"createdAt"))
                .stream()
                .map(this::mapToManagerOutPut)
                .collect(Collectors.toList());
    }

    private ManagerOutPut mapToManagerOutPut(Manager manager) {
        return manager.toManagerOutPut();
    }

    public Manager createManager(Manager manager) {
        return managerRepository.save(manager);
    }
}
