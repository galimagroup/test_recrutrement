package com.test.recrutement.productManagement.service;

import com.test.recrutement.productManagement.model.Wishlist;
import com.test.recrutement.productManagement.model.WishlistItem;
import com.test.recrutement.productManagement.repo.ProductRepository;
import com.test.recrutement.productManagement.repo.WishlistRepository;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {


    private final WishlistRepository wishlistRepository;

    private final ProductRepository productRepository;

    public WishlistService(WishlistRepository wishlistRepository, ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
    }

    public Wishlist getWishlist(String userEmail) {
        return wishlistRepository.findByUserEmail(userEmail)
                .orElseGet(() -> {
                    Wishlist wishlist = new Wishlist();
                    wishlist.setUserEmail(userEmail);
                    return wishlistRepository.save(wishlist);
                });
    }

    public Wishlist addProductToWishlist(String userEmail, Long productId) {
        Wishlist wishlist = getWishlist(userEmail);

        productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        boolean exists = wishlist.getItems().stream()
                .anyMatch(item -> item.getProductId().equals(productId));
        if (exists) {
            throw new RuntimeException("Product already in wishlist");
        }

        WishlistItem item = new WishlistItem();
        item.setProductId(productId);
        item.setWishlist(wishlist);
        wishlist.getItems().add(item);

        return wishlistRepository.save(wishlist);
    }

    public Wishlist removeProductFromWishlist(String userEmail, Long productId) {
        Wishlist wishlist = getWishlist(userEmail);

        wishlist.getItems().removeIf(item -> item.getProductId().equals(productId));

        return wishlistRepository.save(wishlist);
    }

    public Wishlist clearWishlist(String userEmail) {
        Wishlist wishlist = getWishlist(userEmail);
        wishlist.getItems().clear();
        return wishlistRepository.save(wishlist);
    }
}

