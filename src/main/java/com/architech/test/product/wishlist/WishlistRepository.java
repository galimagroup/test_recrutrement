package com.architech.test.product.wishlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<Wishlist> findByUserIdAndIsDefaultTrue(Long userId);

    @Query("SELECT w FROM Wishlist w WHERE w.user.id = :userId AND w.name = :name")
    Optional<Wishlist> findByUserIdAndName(@Param("userId") Long userId, @Param("name") String name);

    List<Wishlist> findByIsPublicTrueOrderByCreatedAtDesc();
}
