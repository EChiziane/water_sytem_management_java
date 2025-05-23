package com.api.water_sytem_management_java.services;

import com.api.water_sytem_management_java.controllers.dtos.SprintInput;
import com.api.water_sytem_management_java.controllers.dtos.SprintOutput;
import com.api.water_sytem_management_java.models.Sprint;

import com.api.water_sytem_management_java.repositories.SprintRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SprintService {
    private final SprintRepository sprintRepository;

    public SprintService(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    public List<SprintOutput> getAllSprints() {
        return sprintRepository.findAll().stream()
                .map(Sprint::toSprintOutput)
                .collect(Collectors.toList());
    }

    public Sprint createSprint(Sprint sprint) {
        return sprintRepository.save(sprint);
    }

    @Transactional
    public Optional<SprintOutput> sprintUpdate(@PathVariable UUID id, SprintInput input) {
        return sprintRepository.findById(id)
                .map(existingSprint->{
                    existingSprint.setName(input.name());
                    existingSprint.setCode(input.code());
                    existingSprint.setStatus(input.status());
                    existingSprint.setDescription(input.description());
                    Sprint updatedSprint = sprintRepository.save(existingSprint);
                    return mapToSprintOutPut(updatedSprint);

                });
    }

    private SprintOutput mapToSprintOutPut(Sprint sprint) {
        return sprint.toSprintOutput();
    }

    public void deleteSprint(@PathVariable UUID id) {
        sprintRepository.deleteById(id);
    }
}
