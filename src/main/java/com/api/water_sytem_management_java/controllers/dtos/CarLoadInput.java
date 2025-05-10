package com.api.water_sytem_management_java.controllers.dtos;

import com.api.water_sytem_management_java.models.CarLoad;

import java.math.BigDecimal;

public record CarLoadInput(
        String deliveryDestination,    // Final delivery location
        String customerName,           // Name of the customer receiving materials
        String logisticsManagerName,   // Person managing the shipment
        String assignedDriverName,     // Driver responsible for the delivery
        String transportedMaterial,    // Name/type of material being delivered
        String carloadBatchName,       // Name of the delivery sprint or batch
        String customerPhoneNumber,    // Contact phone number of the customer
        BigDecimal totalSpent,         // Money spent on the delivery
        BigDecimal totalEarnings,      // Revenue from the delivery
        String deliveryStatus          // Current status: e.g., "pending", "completed"
) {
    public CarLoad toCarLoad() {
        return new CarLoad(
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
}
