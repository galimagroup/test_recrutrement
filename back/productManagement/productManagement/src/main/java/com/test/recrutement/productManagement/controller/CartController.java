package com.test.recrutement.productManagement.controller;

import com.test.recrutement.productManagement.model.Cart;
import com.test.recrutement.productManagement.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/cart")
@RestController
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<Cart> getCart(Principal principal) {
        String userEmail = principal.getName();
        return ResponseEntity.ok(cartService.getCart(userEmail));
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addProductToCart(
            Principal principal,
            @RequestParam Long productId,
            @RequestParam int quantity
    ) {
        String userEmail = principal.getName();

        return ResponseEntity.ok(cartService.addProductToCart(userEmail, productId, quantity));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Cart> removeProductFromCart(
            Principal principal,
            @RequestParam Long productId) {
        String userEmail = principal.getName();
        return ResponseEntity.ok(cartService.removeProductFromCart(userEmail, productId));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Cart> clearCart(Principal principal) {
        String userEmail = principal.getName();
        return ResponseEntity.ok(cartService.clearCart(userEmail));
    }
}
