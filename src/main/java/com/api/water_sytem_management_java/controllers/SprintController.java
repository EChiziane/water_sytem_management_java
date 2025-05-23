package com.api.water_sytem_management_java.controllers;

import com.api.water_sytem_management_java.controllers.dtos.SprintInput;
import com.api.water_sytem_management_java.controllers.dtos.SprintOutput;
import com.api.water_sytem_management_java.models.Sprint;
import com.api.water_sytem_management_java.services.SprintService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/sprints")
public class SprintController {

    private final SprintService sprintService;

    public SprintController(SprintService sprintService) {
        this.sprintService = sprintService;
    }

    @GetMapping
    public ResponseEntity<List<SprintOutput>> getAllSprints() {
        List<SprintOutput> sprints = sprintService.getAllSprints();
        return ResponseEntity.ok(sprints);
    }

    @PostMapping
    public ResponseEntity<Sprint> createSprint(@RequestBody SprintInput sprintInput) {
        Sprint sprint = sprintInput.toSprint();
        Sprint savedSprint = sprintService.createSprint(sprint);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSprint);
    }


    @PutMapping("/{id}")
    public ResponseEntity<SprintOutput> updateSprint(@PathVariable UUID id, @RequestBody SprintInput sprintInput) {
        Optional<SprintOutput> updatedSprint = sprintService.sprintUpdate(id, sprintInput);
        return updatedSprint.map(sprint -> ResponseEntity.ok().body(sprint)).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Sprint> deleteSprint(@PathVariable UUID id) {
        sprintService.deleteSprint(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
