package com.galimagroup.gestionproduit.service.serviceInterface;

import com.galimagroup.gestionproduit.entity.Panier;
import com.galimagroup.gestionproduit.entity.Souhait;

public interface SouhaitServiceInterface {
    void ajoutProduitToSouhait(Long userId, Long productId);

    void supprimerProduitToSouhait(Long userId, Long productId);

    Souhait getSouhaitByUserId(Long userId);
}
