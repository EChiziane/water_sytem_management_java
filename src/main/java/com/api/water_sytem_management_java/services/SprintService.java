package com.api.water_sytem_management_java.services;

import com.api.water_sytem_management_java.models.Sprint;
import com.api.water_sytem_management_java.repositories.SprintRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class SprintService {
    private  final SprintRepository sprintRepository;

    public SprintService(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    public Sprint createSprint(Sprint sprint) {
        return sprintRepository.save(sprint);
    }

    public List<SprintOutPut> getAllSprints() {
        return sprintRepository.findAll(Sort.by(Sort.Direction.DESC,"createdAt"))
                .stream()
                .map(this::mapToSprintOutPut)
                .collect(Collectors.toList());
    }

    public  SprintOutPut mapToSprintOutPut(Sprint sprint) {
        return sprint.toSprintOutPut();
    }
}
