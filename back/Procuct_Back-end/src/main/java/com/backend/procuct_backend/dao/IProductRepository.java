package com.backend.procuct_backend.dao;

import com.backend.procuct_backend.entitie.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<ProductEntity, Integer> {
    boolean existsByCode(String code);
}
