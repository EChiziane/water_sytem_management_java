package com.api.water_sytem_management_java.models;

import com.api.water_sytem_management_java.controllers.dtos.ManagerOutPut;
import com.api.water_sytem_management_java.controllers.dtos.ManagerStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.UUID;



@Getter
@Setter
@Entity
@Table(name = "tb_managers")

public class Manager {
    @Serial
    private static final long serialVersionUID = 1L;
    private final LocalDateTime createdAt = LocalDateTime.now(); // Use LocalDateTime.now() directly
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String contact;
    private String address;
    private ManagerStatus status;

    public Manager(String name, String contact, String address, ManagerStatus status) {
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.status = status;
    }

    public Manager() {

    }

    public ManagerOutPut toManagerOutPut() {
        return  new ManagerOutPut(
                id,
                name,
                contact,
                address,
                status,
                createdAt
        );
    }
}
