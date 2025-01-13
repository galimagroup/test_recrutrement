package com.shop.alten.test_technique.repository;

import com.shop.alten.test_technique.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByProductId(Long productId);

    List<Cart> findByUserId(Long userId);

    @Query("SELECT SUM(c.quantity) FROM Cart c WHERE c.user.id = :userId")
    Long getTotalProductsByUserId(@Param("userId") Long userId);
}
