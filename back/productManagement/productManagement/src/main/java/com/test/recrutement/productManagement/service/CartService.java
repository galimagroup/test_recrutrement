package com.test.recrutement.productManagement.service;

import com.test.recrutement.productManagement.model.Cart;
import com.test.recrutement.productManagement.model.CartItem;
import com.test.recrutement.productManagement.repo.CartRepository;
import com.test.recrutement.productManagement.repo.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Cart addProductToCart(String userEmail, Long productId, int quantity) {
        Cart cart = getCart(userEmail);

        productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));


        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProducId().equals(productId))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setProducId(productId);
                    newItem.setQuantity(0);
                    newItem.setCart(cart);
                    cart.getItems().add(newItem);

                    return newItem;
                });

        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        return cartRepository.save(cart);
    }

    public Cart getCart(String userEmail) {
        return cartRepository.findByUserEmail(userEmail)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUserEmail(userEmail);
                    return cartRepository.save(cart);
                });
    }

    public Cart removeProductFromCart(String userEmail, Long productId) {
        Cart cart = getCart(userEmail);

        cart.getItems().removeIf(item -> item.getProducId().equals(productId));

        return cartRepository.save(cart);
    }

    public Cart clearCart(String userEmail) {
        Cart cart = getCart(userEmail);
        cart.getItems().clear();
        return cartRepository.save(cart);
    }

}
