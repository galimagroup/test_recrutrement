package com.galimagroup.back.service;

import com.galimagroup.back.model.Users;
import com.galimagroup.back.model.Product;
import com.galimagroup.back.model.WishlistItem;
import com.galimagroup.back.repository.UserRepository;
import com.galimagroup.back.repository.ProductRepository;
import com.galimagroup.back.repository.WishlistItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.galimagroup.back.model.WishlistItem;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistItemRepository wishlistItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public List<WishlistItem> getWishlistForUser(String email) {
        Users user = userRepository.findByEmail(email).orElseThrow();
        return wishlistItemRepository.findByUser(user);
    }

    public WishlistItem addToWishlist(String email, Long productId) {
        Users user = userRepository.findByEmail(email).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();
        return wishlistItemRepository.findByUserAndProduct(user, product)
                .orElseGet(() -> wishlistItemRepository.save(new WishlistItem(user, product)));
    }

    public void removeFromWishlist(String email, Long productId) {
        Users user = userRepository.findByEmail(email).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();
        wishlistItemRepository.deleteByUserAndProduct(user, product);
    }
}
