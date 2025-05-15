package com.api.water_sytem_management_java.models;

import com.api.water_sytem_management_java.services.SprintOutPut;
import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "tb_sprints")

public class Sprint {
    @Serial
    private static final long serialVersionUID = 1L;
    private final LocalDateTime createdAt = LocalDateTime.now(); // Use LocalDateTime.now() directly
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String Name;
    private String Description;
    private String Status;


    public Sprint(String name, String description) {
        this.Name = name;
        this.Description = description;
        this.Status="CREATED";
    }

    public Sprint() {

    }

    public SprintOutPut toSprintOutPut() {
        return new SprintOutPut(Name, Description, Status);
    }
}
