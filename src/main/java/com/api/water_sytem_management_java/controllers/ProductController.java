package com.api.water_sytem_management_java.controllers;


import com.api.water_sytem_management_java.models.product.Product;
import com.api.water_sytem_management_java.controllers.dtos.ProductRequestDTO;
import com.api.water_sytem_management_java.controllers.dtos.ProductResponseDTO;
import com.api.water_sytem_management_java.repositories.ProductRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("product")
/*@RequestMapping(value = "/teste-open-api", produces = {"application/json"})*/
@Tag(name = "product")
public class ProductController {

    @Autowired
    ProductRepository repository;

    @PostMapping
    public ResponseEntity postProduct(@RequestBody @Valid ProductRequestDTO body){
        Product newProduct = new Product(body);

        this.repository.save(newProduct);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity getAllProducts(){
        List<ProductResponseDTO> productList = this.repository.findAll().stream().map(ProductResponseDTO::new).toList();

        return ResponseEntity.ok(productList);
    }
}