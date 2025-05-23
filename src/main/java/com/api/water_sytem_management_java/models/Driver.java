package com.api.water_sytem_management_java.models;

import com.api.water_sytem_management_java.controllers.dtos.DriverStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_drivers")
public class Driver {
    @Serial
    private static final long serialVersionUID = 1L;
    private final LocalDateTime createdAt = LocalDateTime.now(); // Use LocalDateTime.now() directly
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String Name;
    private String Phone;
    private String CarDescription;
    private    DriverStatus status;

    public Driver(String name,
                  String contact,
                  String carDescription,
                  DriverStatus status) {
        this.Name = name;
        this.Phone = contact;
        this.CarDescription = carDescription;
        this.status = status;
    }

    public Driver() {

    }

    public DriverOutPut toDriverOutPut(Driver driver) {
        return new DriverOutPut(id,
                Name,
                Phone,
                CarDescription,
                status,
                createdAt
        );
    }
}
