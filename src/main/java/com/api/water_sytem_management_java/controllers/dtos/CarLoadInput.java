package com.api.water_sytem_management_java.controllers.dtos;

import com.api.water_sytem_management_java.models.CarLoad;
import com.api.water_sytem_management_java.models.Driver;
import com.api.water_sytem_management_java.models.Manager;
import com.api.water_sytem_management_java.models.Sprint;

import java.math.BigDecimal;
import java.util.UUID;

public record CarLoadInput(
        String deliveryDestination,    // Final delivery location
        String customerName,           // Name of the customer receiving materials
        UUID logisticsManagerId,   // Person managing the shipment
       UUID assignedDriverId,     // Driver responsible for the delivery
        String transportedMaterial,    // Name/type of material being delivered
        UUID carloadBatchId,       // Name of the delivery sprint or batch
        String customerPhoneNumber,    // Contact phone number of the customer
        BigDecimal totalSpent,         // Money spent on the delivery
        BigDecimal totalEarnings,      // Revenue from the delivery
        String deliveryStatus          // Current status: e.g., "pending", "completed"
) {
    public CarLoad toCarLoad(Manager manager, Driver assignedDriver, Sprint carloadBatchName) {
        return new CarLoad(
                deliveryDestination,
                customerName,
                manager,
                assignedDriver,
                transportedMaterial,
                carloadBatchName,
                customerPhoneNumber,
                totalSpent,
                totalEarnings,
                deliveryStatus
        );
    }
}
