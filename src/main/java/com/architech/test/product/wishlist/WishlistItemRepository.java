package com.architech.test.product.wishlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {

    List<WishlistItem> findByWishlistIdOrderByAddedAtDesc(Long wishlistId);

    Optional<WishlistItem> findByWishlistIdAndProductId(Long wishlistId, Long productId);

    @Query("SELECT wi FROM WishlistItem wi WHERE wi.wishlist.user.id = :userId AND wi.product.id = :productId")
    List<WishlistItem> findByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

    void deleteByWishlistIdAndProductId(Long wishlistId, Long productId);

    @Query("SELECT COUNT(wi) FROM WishlistItem wi WHERE wi.product.id = :productId")
    Long countByProductId(@Param("productId") Long productId);
}
