package com.galimagroup.back.service;

import com.galimagroup.back.entities.Wishlist;
import com.galimagroup.back.entities.WishlistItem;

public interface WishlistService {

    // Récupérer l'envie d'un utilisateur
    public Wishlist getWishlistByUserId(String userId);

    // Ajouter un produit à l'envie
    public Wishlist addItemToWishlist(String userId, WishlistItem item);

    // Supprimer un produit de l'envie
    public Wishlist removeItemFromWishlist(String userId, String productId);

    // Vider l'envie
    public void clearWishlist(String userId) ;

}
