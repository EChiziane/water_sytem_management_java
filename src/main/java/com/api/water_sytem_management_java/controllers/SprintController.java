package com.api.water_sytem_management_java.controllers;


import com.api.water_sytem_management_java.controllers.dtos.SprintInput;
import com.api.water_sytem_management_java.models.Sprint;
import com.api.water_sytem_management_java.services.SprintOutPut;
import com.api.water_sytem_management_java.services.SprintService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/sprints")
public class SprintController {
    private final SprintService sprintService;

    public SprintController(SprintService sprintService) {
        this.sprintService = sprintService;
    }

    @PostMapping
    public ResponseEntity<Sprint> createSprint(@RequestBody SprintInput sprintInput){
        Sprint sprint = sprintInput.toSprint();
        Sprint savedSprint= sprintService.createSprint(sprint);
        return  ResponseEntity.status(HttpStatus.CREATED).body(savedSprint);
    }
    @GetMapping
    ResponseEntity<List<SprintOutPut>> getAllSprints(){
        List<SprintOutPut> sprints = sprintService.getAllSprints();
        return  ResponseEntity.ok(sprints);
    }
}
