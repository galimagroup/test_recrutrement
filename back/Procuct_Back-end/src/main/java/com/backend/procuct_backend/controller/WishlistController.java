package com.backend.procuct_backend.controller;

import com.backend.procuct_backend.dto.Wishlist;
import com.backend.procuct_backend.service.WishlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public ResponseEntity<Wishlist> getUserWishlist() {
        Wishlist wishlist = wishlistService.getUserWishlist();
        return ResponseEntity.ok(wishlist);
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<Wishlist> addProductToWishlist(@PathVariable int productId) {
        Wishlist wishlist = wishlistService.addProductToWishlist(productId);
        return ResponseEntity.ok(wishlist);
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Wishlist> removeProductFromWishlist(@PathVariable int productId) {
        Wishlist wishlist = wishlistService.removeProductFromWishlist(productId);
        return ResponseEntity.ok(wishlist);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearWishlist() {
        wishlistService.clearWishlist();
        return ResponseEntity.ok("Liste d'envie vidée avec succès");
    }

    @GetMapping("/check/{productId}")
    public ResponseEntity<Boolean> isProductInWishlist(@PathVariable int productId) {
        boolean isInWishlist = wishlistService.isProductInWishlist(productId);
        return ResponseEntity.ok(isInWishlist);
    }
}