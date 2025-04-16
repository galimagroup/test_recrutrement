package com.galimagroup.back.repository;

import com.galimagroup.back.model.WishlistItem;
import com.galimagroup.back.model.Users;
import com.galimagroup.back.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findByUser(Users user);
    Optional<WishlistItem> findByUserAndProduct(Users user, Product product);
    void deleteByUserAndProduct(Users user, Product product);
}
