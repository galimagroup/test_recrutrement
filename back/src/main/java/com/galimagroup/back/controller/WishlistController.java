package com.galimagroup.back.controller;

import com.galimagroup.back.model.WishlistItem;
import com.galimagroup.back.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class WishlistController {
    private final WishlistService wishlistService;

    @GetMapping
    public ResponseEntity<List<WishlistItem>> getWishlist(@AuthenticationPrincipal UserDetails userDetails) {
        List<WishlistItem> wishlist = wishlistService.getWishlistForUser(userDetails.getUsername());
        return ResponseEntity.ok(wishlist);
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<WishlistItem> addToWishlist(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long productId) {
        WishlistItem item = wishlistService.addToWishlist(userDetails.getUsername(), productId);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Void> removeFromWishlist(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long productId) {
        wishlistService.removeFromWishlist(userDetails.getUsername(), productId);
        return ResponseEntity.ok().build();
    }
}
