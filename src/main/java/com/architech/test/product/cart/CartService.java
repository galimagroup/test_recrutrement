package com.architech.test.product.cart;

import com.architech.test.product.dto.AddToCartRequest;
import com.architech.test.product.dto.CartSummaryDto;
import com.architech.test.product.dto.UpdateCartItemRequest;

public interface CartService {

    Cart getOrCreateCart(String userEmail);
    Cart addToCart(String userEmail, AddToCartRequest request);
    Cart updateCartItem(String userEmail, Long productId, UpdateCartItemRequest request);
    Cart removeFromCart(String userEmail, Long productId);
    CartSummaryDto getCartSummary(String userEmail);
    void clearCart(String userEmail);
}
