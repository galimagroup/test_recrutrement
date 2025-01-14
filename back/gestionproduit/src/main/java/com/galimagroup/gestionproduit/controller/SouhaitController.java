package com.galimagroup.gestionproduit.controller;

import com.galimagroup.gestionproduit.entity.Panier;
import com.galimagroup.gestionproduit.entity.Souhait;
import com.galimagroup.gestionproduit.service.serviceImp.PanierServiceImpl;
import com.galimagroup.gestionproduit.service.serviceImp.SouhaitServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/souhait")
public class SouhaitController {

    private final SouhaitServiceImpl souhaitService;

    public SouhaitController(SouhaitServiceImpl souhaitService) {
        this.souhaitService = souhaitService;
    }

    @PostMapping("/{userId}/add/{productId}")
    public ResponseEntity<String> addToCart(@PathVariable Long userId, @PathVariable Long productId) {
        souhaitService.ajoutProduitToSouhait(userId, productId);
        return ResponseEntity.ok("Produit ajouter");
    }

    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long userId, @PathVariable Long productId) {
        souhaitService.supprimerProduitToSouhait(userId, productId);
        return ResponseEntity.ok("Produit supprimer ");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Souhait> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(souhaitService.getSouhaitByUserId(userId));
    }
}
