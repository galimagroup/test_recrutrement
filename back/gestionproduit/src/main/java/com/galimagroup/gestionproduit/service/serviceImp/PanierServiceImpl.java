package com.galimagroup.gestionproduit.service.serviceImp;

import com.galimagroup.gestionproduit.entity.Panier;
import com.galimagroup.gestionproduit.entity.Produit;
import com.galimagroup.gestionproduit.entity.User;
import com.galimagroup.gestionproduit.repo.PanierRepository;
import com.galimagroup.gestionproduit.repo.ProduitRepository;
import com.galimagroup.gestionproduit.repo.UtilisateurRepository;
import com.galimagroup.gestionproduit.service.serviceInterface.PanierServiceInterface;
import org.springframework.stereotype.Service;

@Service
public class PanierServiceImpl implements PanierServiceInterface {
    private final PanierRepository panierRepository;
    private final ProduitRepository produitRepository;
    private final UtilisateurRepository userRepository;

    public PanierServiceImpl(PanierRepository panierRepository, ProduitRepository produitRepository, UtilisateurRepository userRepository) {
        this.panierRepository = panierRepository;
        this.produitRepository = produitRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void ajoutProduitToPanier(Long userId, Long productId) {
        Panier cart = getOrCreateCart(userId);
        Produit product = produitRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        cart.getProducts().add(product);
        panierRepository.save(cart);
    }

    @Override
    public void supprimerProduitToPanier(Long userId, Long productId) {
        Panier cart = getOrCreateCart(userId);
        Produit product = produitRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        cart.getProducts().remove(product);
        panierRepository.save(cart);
    }

    @Override
    public Panier getCartByUserId(Long userId) {

        return panierRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));    }
    private Panier getOrCreateCart(Long userId) {
        return panierRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    Panier cart = new Panier();
                    cart.setUser(user);
                    return panierRepository.save(cart);
                });
    }
}
