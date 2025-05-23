package com.api.water_sytem_management_java.services;

import com.api.water_sytem_management_java.controllers.dtos.ManagerInPut;
import com.api.water_sytem_management_java.controllers.dtos.ManagerOutPut;
import com.api.water_sytem_management_java.models.Manager;
import com.api.water_sytem_management_java.repositories.ManagerRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;

    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }


    public List<ManagerOutPut> getAllManagers() {
        return managerRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
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

    public void deleteManager(@PathVariable UUID id) {
        managerRepository.deleteById(id);
    }

    @Transactional
    public Optional<ManagerOutPut> updateManager(@PathVariable UUID id, ManagerInPut managerInPut) {
        return managerRepository.findById(id)
                .map(existingManager -> {
                    existingManager.setName(managerInPut.name());
                    existingManager.setAddress(managerInPut.address());
                    existingManager.setContact(managerInPut.contact());
                    existingManager.setStatus(managerInPut.status());
                    Manager updatedManager = managerRepository.save(existingManager);
                    return mapToManagerOutPut(existingManager);
                });
    }
}
