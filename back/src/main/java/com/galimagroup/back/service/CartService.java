package com.galimagroup.back.service;

import org.springframework.stereotype.Service;
import com.galimagroup.back.model.Cart;
import com.galimagroup.back.model.CartItem;
import com.galimagroup.back.model.Product;
import com.galimagroup.back.model.User;
import com.galimagroup.back.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {
    
    private final CartRepository cartRepository;
    private final ProductService productService;

    public Cart getCartForUser(User user) {
        return cartRepository.findByUser(user)
            .orElseGet(() -> {
                Cart newCart = new Cart();
                newCart.setUser(user);
                return cartRepository.save(newCart);
            });
    }

    @Transactional
    public Cart addToCart(User user, Long productId, Integer quantity) {
        Cart cart = getCartForUser(user);
        Product product = productService.getProduct(productId);
        
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        
        cart.getItems().add(cartItem);
        cart.setTotal(calculateTotal(cart));
        
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeFromCart(User user, Long productId) {
        Cart cart = getCartForUser(user);
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cart.setTotal(calculateTotal(cart));
        return cartRepository.save(cart);
    }

    private Double calculateTotal(Cart cart) {
        return cart.getItems().stream()
            .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
            .sum();
    }
}
