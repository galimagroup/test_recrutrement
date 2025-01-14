package com.galimagroup.gestionproduit.service.serviceInterface;

import com.galimagroup.gestionproduit.entity.Panier;

public interface PanierServiceInterface {
    void ajoutProduitToPanier(Long userId, Long productId);

    void supprimerProduitToPanier(Long userId, Long productId);

    Panier getCartByUserId(Long userId);
}
