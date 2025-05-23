package com.api.water_sytem_management_java.models;

import com.api.water_sytem_management_java.controllers.dtos.SprintOutput;
import com.api.water_sytem_management_java.controllers.dtos.SprintStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_sprints")
public class Sprint implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final LocalDateTime createdAt = LocalDateTime.now(); // Use LocalDateTime.now() directly

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;

    private String code;
    private String description;
    private SprintStatus status;

    public Sprint() {
    }

    public Sprint(String name, String code, String description, SprintStatus status) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public SprintOutput toSprintOutput() {
        return new SprintOutput(id, name, code, description, status, createdAt);
    }

    // Getters and setters (opcional)
}
