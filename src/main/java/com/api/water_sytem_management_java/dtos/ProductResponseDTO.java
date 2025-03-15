package com.api.water_sytem_management_java.dtos;


import com.api.water_sytem_management_java.domain.product.Product;

public record ProductResponseDTO(String id, String name, Integer price) {
    public ProductResponseDTO(Product product){
        this(product.getId(), product.getName(), product.getPrice());
    }
}