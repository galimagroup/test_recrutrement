package com.shop.alten.test_technique.service;

import com.shop.alten.test_technique.interfaces.ICart;
import com.shop.alten.test_technique.model.Cart;
import com.shop.alten.test_technique.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService implements ICart {
    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart addToCart(Cart cart) {
        // Vérifiez si un produit similaire est déjà dans le panier
        Cart existingCart = cartRepository.findByProductId(cart.getProduct().getId());
        if (existingCart != null) {
            // Si le produit existe déjà, mettez à jour la quantité
            existingCart.setQuantity(existingCart.getQuantity() + cart.getQuantity());
            return cartRepository.save(existingCart);
        }
        return cartRepository.save(cart);
    }

    @Override
    public List<Cart> getCartItemsByUserId(Long userId) {
        return cartRepository.findByUserId(userId); // Assurez-vous que `Product` a une relation avec `User`.
    }

    @Override
    public Long getTotalProductsByUser(Long userId) {
        return cartRepository.getTotalProductsByUserId(userId);
    }

    @Override
    public void updateCartItem(Long id, Integer quantity) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new RuntimeException("Cart item not found"));
        cart.setQuantity(quantity);
        cartRepository.save(cart);
    }

    @Override
    public void deleteCartItem(Long id) {
        cartRepository.deleteById(id);
    }
}
