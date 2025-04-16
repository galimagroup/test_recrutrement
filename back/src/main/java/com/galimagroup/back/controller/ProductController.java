package com.galimagroup.back.controller;

import com.galimagroup.back.dto.ProductDto;
import com.galimagroup.back.security.JwtTokenProvider;
import com.galimagroup.back.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductService productService;
    private final JwtTokenProvider jwtTokenProvider;

    // Permet à tous les utilisateurs authentifiés de consulter les produits
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // Permet à tous les utilisateurs authentifiés de consulter un produit spécifique
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    // Permet uniquement à l'admin de créer un produit
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestHeader("Authorization") String authHeader, @RequestBody ProductDto productDto) {
        String token = authHeader.replace("Bearer ", "");

        // Vérification de l'admin via le JWT
        if (!jwtTokenProvider.isAdmin(token)) {
            return ResponseEntity.status(403).body(null); 
        }

        return ResponseEntity.status(201).body(productService.createProduct(productDto));
    }

    // Permet uniquement à l'admin de mettre à jour un produit
    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@RequestHeader("Authorization") String authHeader, @PathVariable Long id, @RequestBody ProductDto productDto) {
        String token = authHeader.replace("Bearer ", "");

        // Vérification de l'admin via le JWT
        if (!jwtTokenProvider.isAdmin(token)) {
            return ResponseEntity.status(403).body(null); // Accès refusé
        }

        return ResponseEntity.ok(productService.updateProduct(id, productDto));
    }

    // Permet uniquement à l'admin de supprimer un produit
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        String token = authHeader.replace("Bearer ", "");

        // Vérification de l'admin via le JWT
        if (!jwtTokenProvider.isAdmin(token)) {
            return ResponseEntity.status(403).body(null); // Accès refusé
        }

        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
