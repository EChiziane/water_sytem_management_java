package com.api.water_sytem_management_java.models;

import com.api.water_sytem_management_java.controllers.dtos.SprintOutput;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "tb_sprints")
public class Sprint implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String code;
    private String descricao;

    public Sprint() {}

    public Sprint(String code, String descricao) {
        this.code = code;
        this.descricao = descricao;
    }

    public SprintOutput toSprintOutput() {
        return new SprintOutput(code, descricao);
    }

    // Getters and setters (opcional)
}
