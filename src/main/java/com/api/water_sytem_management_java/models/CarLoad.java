package com.api.water_sytem_management_java.models;

import com.api.water_sytem_management_java.controllers.dtos.CarLoadOutPut;
import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "tb_carloads")
public class CarLoad implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final LocalDateTime createdAt = LocalDateTime.now(); // Use LocalDateTime.now() directly
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String deliveryDestination;    // Final delivery location
    private String customerName;           // Name of the customer receiving materials

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "manager_id")
    private Manager logisticsManagerName;


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "driver_id")
    private Driver assignedDriver;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "sprint_id")
    private Sprint carloadBatchSprint;


    // Driver responsible for the delivery
    private String transportedMaterial;    // Name/type of material being delivered
    private String carloadBatchName;     // Name of the delivery sprint or batch
    private String customerPhoneNumber;    // Contact phone number of the customer
    private BigDecimal totalSpent;         // Money spent on the delivery
    private BigDecimal totalEarnings;      // Revenue from the delivery
    private String deliveryStatus;         // Current status: e.g., "pending", "completed"

    public CarLoad() {
    }

    public CarLoad(String deliveryDestination,
                   String customerName,
                   Manager logisticsManagerName,
                   Driver assignedDriver,
                   String transportedMaterial,
                   Sprint carloadBatchName,
                   String customerPhoneNumber,
                   BigDecimal totalSpent,
                   BigDecimal totalEarnings,
                   String deliveryStatus) {
        this.deliveryDestination = deliveryDestination;
        this.customerName = customerName;
        this.logisticsManagerName = logisticsManagerName;
        this.assignedDriver = assignedDriver;
        this.transportedMaterial = transportedMaterial;
        this.carloadBatchSprint = carloadBatchName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.totalSpent = totalSpent;
        this.totalEarnings = totalEarnings;
        this.deliveryStatus = deliveryStatus;
    }

    public CarLoadOutPut toCarLoadOutPut() {
        return new CarLoadOutPut(
                deliveryDestination,
                customerName,
                logisticsManagerName.getName(),
                assignedDriver.getName(),
                transportedMaterial,
                carloadBatchSprint.getName(),
                customerPhoneNumber,
                totalSpent,
                totalEarnings,
                deliveryStatus
        );
    }

}
