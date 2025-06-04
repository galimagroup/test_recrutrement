package com.backend.procuct_backend.dao;

import com.backend.procuct_backend.entitie.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IWishlistRepository extends JpaRepository<WishlistEntity, Integer> {
    Optional<WishlistEntity> findByUserId(int userId);
    Optional<WishlistEntity> findByUserEmail(String email);
}