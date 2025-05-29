package com.api.water_sytem_management_java.repositories;

import com.api.water_sytem_management_java.models.CarLoad;
import com.api.water_sytem_management_java.models.Customer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarLoadRepository extends JpaRepository<CarLoad, UUID> {
    List<CarLoad> findByCarloadBatchSprintId(UUID id);
}
