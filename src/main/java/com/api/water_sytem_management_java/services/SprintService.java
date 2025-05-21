package com.api.water_sytem_management_java.services;

import com.api.water_sytem_management_java.controllers.dtos.SprintOutput;
import com.api.water_sytem_management_java.models.Sprint;
import com.api.water_sytem_management_java.repositories.SprintRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
