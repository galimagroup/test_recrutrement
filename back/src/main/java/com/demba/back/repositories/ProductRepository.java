package com.demba.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demba.back.models.Product;

import jakarta.transaction.Transactional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Transactional
    @Query(value = "delete from `desired_products` where `product_id`=:productId and `user_id`=:userId", nativeQuery = true)
    void deleteDesiredProduct(@Param("productId") Long productId, @Param("userId") Long userId);
}
