package com.backend.procuct_backend.dao;

import com.backend.procuct_backend.entitie.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ICartItemRepository extends JpaRepository<CartItemEntity, Integer> {
    Optional<CartItemEntity> findByCartIdAndProductId(int cartId, int productId);
    void deleteByCartIdAndProductId(int cartId, int productId);
}
