package com.galimagroup.back.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.galimagroup.back.model.Cart;
import com.galimagroup.back.model.User;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
