package com.shop.alten.test_technique.interfaces;

import com.shop.alten.test_technique.model.Cart;

import java.util.List;

public interface ICart {
    Cart addToCart(Cart cart);

    List<Cart> getCartItemsByUserId(Long userId);

    void updateCartItem(Long id, Integer quantity);

    void deleteCartItem(Long id);

    Long getTotalProductsByUser(Long userId);
}
