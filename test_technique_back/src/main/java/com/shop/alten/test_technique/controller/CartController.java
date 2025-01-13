package com.shop.alten.test_technique.controller;

import com.shop.alten.test_technique.interfaces.ICart;
import com.shop.alten.test_technique.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private ICart cartService;

    @PostMapping
    public ResponseEntity<Cart> addToCart(@RequestBody Cart cart) {
        Cart savedCart = cartService.addToCart(cart);
        return ResponseEntity.ok(savedCart);
    }

    @GetMapping("/total-products/{userId}")
    public ResponseEntity<Long> getTotalProductsByUser(@PathVariable Long userId) {
        Long totalProducts = cartService.getTotalProductsByUser(userId);
        return ResponseEntity.ok(totalProducts != null ? totalProducts : 0L);
    }
    @GetMapping("/user/{userId}")
    public List<Cart> getCartItemsByUserId(@PathVariable Long userId) {
        return cartService.getCartItemsByUserId(userId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCartItem(@PathVariable Long id, @RequestBody Map<String, Integer> request) {
        Integer quantity = request.get("quantity");
        cartService.updateCartItem(id, quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        cartService.deleteCartItem(id);
        return ResponseEntity.ok().build();
    }


}
