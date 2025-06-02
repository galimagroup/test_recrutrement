package com.architech.test.product.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserEmail(String userEmail);

    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.items ci LEFT JOIN FETCH ci.product WHERE c.userEmail = :userEmail")
    Optional<Cart> findByUserEmailWithItems(@Param("userEmail") String userEmail);

    boolean existsByUserEmail(String userEmail);

    void deleteByUserEmail(String userEmail);
}
