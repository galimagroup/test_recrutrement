package com.backend.procuct_backend.dao;

import com.backend.procuct_backend.entitie.WishlistItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IWishlistItemRepository extends JpaRepository<WishlistItemEntity, Integer> {
    Optional<WishlistItemEntity> findByWishlistIdAndProductId(int wishlistId, int productId);
    void deleteByWishlistIdAndProductId(int wishlistId, int productId);
    boolean existsByWishlistIdAndProductId(int wishlistId, int productId);
}

