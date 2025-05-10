package com.api.water_sytem_management_java.models;

import com.api.water_sytem_management_java.controllers.dtos.DriverStatus;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serial;
import java.util.UUID;

public class Driver {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String Name;
    private String Phone;
    private String CarDescription;
    private DriverStatus status;
}
