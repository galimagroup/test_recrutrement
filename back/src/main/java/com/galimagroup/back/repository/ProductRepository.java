package com.galimagroup.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.galimagroup.back.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
