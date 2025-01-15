package com.alten.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alten.e_commerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    
}
