package com.backend.procuct_backend.dao;

import com.backend.procuct_backend.entitie.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ICartRepository extends JpaRepository<CartEntity, Integer> {
    Optional<CartEntity> findByUserId(int userId);
    Optional<CartEntity> findByUserEmail(String email);
}