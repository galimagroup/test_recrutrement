package com.backend.procuct_backend.controller;

import com.backend.procuct_backend.dto.Cart;
import com.backend.procuct_backend.dto.CartItemRequest;
import com.backend.procuct_backend.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<Cart> getUserCart() {
        Cart cart = cartService.getUserCart();
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addProductToCart(@RequestBody CartItemRequest request) { // ← Utilisez @RequestBody
        Cart cart = cartService.addProductToCart(request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/update")
    public ResponseEntity<Cart> updateCartItemQuantity(@RequestBody CartItemRequest request) {
        Cart cart = cartService.updateCartItemQuantity(request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Cart> removeProductFromCart(@PathVariable int productId) {
        Cart cart = cartService.removeProductFromCart(productId);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok("Panier vidé avec succès");
    }
}
