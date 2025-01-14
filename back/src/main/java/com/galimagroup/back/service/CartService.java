package com.galimagroup.back.service;

import com.galimagroup.back.entities.Cart;
import com.galimagroup.back.entities.CartItem;

public interface CartService {
    // Récupérer le panier d'un utilisateur
    public Cart getCartByUserId(String userId);

    // Ajouter un produit au panier
    public Cart addItemToCart(String userId, CartItem item);

    // Supprimer un produit du panier
    public Cart removeItemFromCart(String userId, String productId);

    // Vider le panier
    public void clearCart(String userId);
}
