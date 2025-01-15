package com.alten.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alten.e_commerce.entity.ShoppingCart;
import com.alten.e_commerce.entity.ShoppingCartKey;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,ShoppingCartKey>{
    
}
