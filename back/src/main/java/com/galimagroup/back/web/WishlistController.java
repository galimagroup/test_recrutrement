package com.galimagroup.back.web;

import com.galimagroup.back.entities.Wishlist;
import com.galimagroup.back.entities.WishlistItem;
import com.galimagroup.back.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    // Récupérer la wishlist de l'utilisateur connecté
    @GetMapping
    public ResponseEntity<Wishlist> getWishlist(Authentication authentication) {
        String userId = authentication.getName();
        Wishlist wishlist = wishlistService.getWishlistByUserId(userId);
        return ResponseEntity.ok(wishlist);
    }

    // Ajouter un produit à la wishlist
    @PostMapping("/add")
    public ResponseEntity<Wishlist> addItemToWishlist(@RequestBody WishlistItem item, Authentication authentication) {
        String userId = authentication.getName();
        Wishlist wishlist = wishlistService.addItemToWishlist(userId, item);
        return ResponseEntity.ok(wishlist);
    }

    // Supprimer un produit de la wishlist
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Wishlist> removeItemFromWishlist(@PathVariable String productId, Authentication authentication) {
        String userId = authentication.getName();
        Wishlist wishlist = wishlistService.removeItemFromWishlist(userId, productId);
        return ResponseEntity.ok(wishlist);
    }

    // Vider la wishlist
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearWishlist(Authentication authentication) {
        String userId = authentication.getName();
        wishlistService.clearWishlist(userId);
        return ResponseEntity.noContent().build();
    }
}
