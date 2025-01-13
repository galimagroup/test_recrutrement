package com.test.recrutement.productManagement.controller;

import com.test.recrutement.productManagement.model.Wishlist;
import com.test.recrutement.productManagement.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public ResponseEntity<Wishlist> getWishlist(Principal principal) {
        String userEmail = principal.getName();
        return ResponseEntity.ok(wishlistService.getWishlist(userEmail));
    }

    @PostMapping("/add")
    public ResponseEntity<Wishlist> addProductToWishlist(
            Principal principal,
            @RequestParam Long productId) {
        String userEmail = principal.getName();
        return ResponseEntity.ok(wishlistService.addProductToWishlist(userEmail, productId));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Wishlist> removeProductFromWishlist(
            Principal principal,
            @RequestParam Long productId) {
        String userEmail = principal.getName();
        return ResponseEntity.ok(wishlistService.removeProductFromWishlist(userEmail, productId));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Wishlist> clearWishlist(Principal principal) {
        String userEmail = principal.getName();
        return ResponseEntity.ok(wishlistService.clearWishlist(userEmail));
    }
}

