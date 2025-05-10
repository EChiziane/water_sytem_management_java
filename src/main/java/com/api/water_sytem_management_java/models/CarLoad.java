package com.api.water_sytem_management_java.models;

import com.api.water_sytem_management_java.controllers.dtos.CarLoadOutPut;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;



@Entity
@Table(name = "tb_carloads")
public class CarLoad implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String deliveryDestination;    // Final delivery location
    private String customerName;           // Name of the customer receiving materials
    private String logisticsManagerName;   // Person managing the shipment
    private String assignedDriverName;     // Driver responsible for the delivery
    private String transportedMaterial;    // Name/type of material being delivered
    private String carloadBatchName;     // Name of the delivery sprint or batch
    private String customerPhoneNumber;    // Contact phone number of the customer
    private BigDecimal totalSpent;         // Money spent on the delivery
    private BigDecimal totalEarnings;      // Revenue from the delivery
    private String deliveryStatus;         // Current status: e.g., "pending", "completed"
    private final LocalDateTime createdAt = LocalDateTime.now(); // Use LocalDateTime.now() directly

    public CarLoad() {
    }

    public CarLoad(String deliveryDestination,
                   String customerName,
                   String logisticsManagerName,
                   String assignedDriverName,
                   String transportedMaterial,
                   String carloadBatchName,
                   String customerPhoneNumber,
                   BigDecimal totalSpent,
                   BigDecimal totalEarnings,
                   String deliveryStatus) {
        this.deliveryDestination = deliveryDestination;
        this.customerName = customerName;
        this.logisticsManagerName = logisticsManagerName;
        this.assignedDriverName = assignedDriverName;
        this.transportedMaterial = transportedMaterial;
        this.carloadBatchName = carloadBatchName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.totalSpent = totalSpent;
        this.totalEarnings = totalEarnings;
        this.deliveryStatus = deliveryStatus;
    }

    public CarLoadOutPut toCarLoadOutPut(){
        return  new CarLoadOutPut(
                deliveryDestination,
                customerName,
                logisticsManagerName,
                assignedDriverName,
                transportedMaterial,
                carloadBatchName,
                customerPhoneNumber,
                totalSpent,
                totalEarnings,
                deliveryStatus
                );
    }



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDeliveryDestination() {
        return deliveryDestination;
    }

    public void setDeliveryDestination(String deliveryDestination) {
        this.deliveryDestination = deliveryDestination;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLogisticsManagerName() {
        return logisticsManagerName;
    }

    public void setLogisticsManagerName(String logisticsManagerName) {
        this.logisticsManagerName = logisticsManagerName;
    }

    public String getAssignedDriverName() {
        return assignedDriverName;
    }

    public void setAssignedDriverName(String assignedDriverName) {
        this.assignedDriverName = assignedDriverName;
    }

    public String getTransportedMaterial() {
        return transportedMaterial;
    }

    public void setTransportedMaterial(String transportedMaterial) {
        this.transportedMaterial = transportedMaterial;
    }

    public String getCarloadBatchName() {
        return carloadBatchName;
    }

    public void setCarloadBatchName(String carloadBatchName) {
        this.carloadBatchName = carloadBatchName;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public BigDecimal getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(BigDecimal totalSpent) {
        this.totalSpent = totalSpent;
    }

    public BigDecimal getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(BigDecimal totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
