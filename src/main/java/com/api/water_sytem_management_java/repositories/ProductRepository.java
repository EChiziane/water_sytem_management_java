package com.api.water_sytem_management_java.repositories;


import com.api.water_sytem_management_java.models.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}