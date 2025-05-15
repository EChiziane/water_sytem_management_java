package com.api.water_sytem_management_java.models;

import com.api.water_sytem_management_java.controllers.dtos.ManagerOutPut;
import jakarta.persistence.*;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.UUID;



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
    private String status;

    public Manager(String name, String contact, String address) {
        this.name = name;
        this.contact = contact;
        this.address = address;
    }

    public Manager() {

    }

    public ManagerOutPut toManagerOutPut() {
        return  new ManagerOutPut(
                name,
                contact,
                address,
                status
        );
    }
}
