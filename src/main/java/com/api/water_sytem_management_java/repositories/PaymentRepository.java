package com.api.water_sytem_management_java.repositories;


import com.api.water_sytem_management_java.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findAll(); // Método para buscar e ordenar por data de criação decrescente

}
