package com.test.recrutement.productManagement.repo;

import com.test.recrutement.productManagement.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

