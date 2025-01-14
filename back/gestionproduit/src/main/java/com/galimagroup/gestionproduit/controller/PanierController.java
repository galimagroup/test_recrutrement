package com.galimagroup.gestionproduit.controller;

import com.galimagroup.gestionproduit.entity.Panier;
import com.galimagroup.gestionproduit.service.serviceImp.PanierServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/panier")
public class PanierController {
    private final PanierServiceImpl cartService;

    public PanierController(PanierServiceImpl cartService) {
        this.cartService = cartService;
    }
    @PostMapping("/{userId}/add/{productId}")
    public ResponseEntity<String> addToCart(@PathVariable Long userId, @PathVariable Long productId) {
        cartService.ajoutProduitToPanier(userId, productId);
        return ResponseEntity.ok("Produit ajouter");
    }

    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long userId, @PathVariable Long productId) {
        cartService.supprimerProduitToPanier(userId, productId);
        return ResponseEntity.ok("Produit supprimer ");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Panier> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }
}
