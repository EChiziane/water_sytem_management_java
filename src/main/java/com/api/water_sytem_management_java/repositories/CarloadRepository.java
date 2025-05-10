package com.api.water_sytem_management_java.repositories;

import com.api.water_sytem_management_java.models.CarLoad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CarloadRepository  extends JpaRepository<CarLoad, UUID> {
}
