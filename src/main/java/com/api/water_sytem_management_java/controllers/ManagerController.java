package com.api.water_sytem_management_java.controllers;


import com.api.water_sytem_management_java.controllers.dtos.ManagerInPut;
import com.api.water_sytem_management_java.controllers.dtos.ManagerOutPut;
import com.api.water_sytem_management_java.models.Manager;
import com.api.water_sytem_management_java.services.ManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/managers")
public class ManagerController {

    public final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping
    public ResponseEntity<List<ManagerOutPut>> getAllManagers() {
        List<ManagerOutPut> managers = managerService.getAllManagers();
        return ResponseEntity.ok(managers);
    }

    @PostMapping
    public ResponseEntity<Manager> createManager(@RequestBody ManagerInPut managerInput) {
        Manager manager = managerInput.toManager();
        Manager savedManager = managerService.createManager(manager);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedManager);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ManagerOutPut> updateManager(@PathVariable UUID id, @RequestBody ManagerInPut managerInput) {
        Optional<ManagerOutPut> updatedManager = managerService.updateManager(id, managerInput);
        return updatedManager.map(manager -> ResponseEntity.ok().body(manager)).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Manager> deleteManager(@PathVariable UUID id) {
        managerService.deleteManager(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
