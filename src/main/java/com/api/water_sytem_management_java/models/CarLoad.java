package com.api.water_sytem_management_java.models;

import com.api.water_sytem_management_java.controllers.dtos.CarLoadOutPut;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "tb_carloads")
@Entity(name = "tb_carloads")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class CarLoad {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private  String ClientName;
    private  String PhoneNumber;
    private String MaterialName;
    private String Destination;
    private Double totalRevenue;
    private Double totalExpenses;
    private final LocalDateTime createdAt = LocalDateTime.now(); // Use LocalDateTime.now() directly


    public CarLoad(String clientName,
                   String phoneNumber,
                   String materialName,
                   String destination,
                   Double totalRevenue,
                   Double totalExpenses) {
        this.ClientName=clientName;
        this.PhoneNumber=phoneNumber;
        this.MaterialName=materialName;
        this.Destination=destination;
        this.totalRevenue=totalRevenue;
        this.totalExpenses= totalExpenses;
    }

    public CarLoadOutPut toCarLoadOutPut(){
        return  new CarLoadOutPut(id,
                ClientName,
                PhoneNumber,
                MaterialName,
                Destination,
                totalRevenue,
                totalExpenses,
                createdAt);
    }

}
