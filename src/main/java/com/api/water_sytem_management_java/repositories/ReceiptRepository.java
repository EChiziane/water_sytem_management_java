package com.api.water_sytem_management_java.repositories;

import com.api.water_sytem_management_java.models.Recibo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReceiptRepository extends JpaRepository<Recibo, UUID> {
    List<Recibo> findAll(Sort sort);
}

