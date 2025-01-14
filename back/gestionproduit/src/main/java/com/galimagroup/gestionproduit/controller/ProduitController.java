package com.galimagroup.gestionproduit.controller;

import com.galimagroup.gestionproduit.dto.ProduitDto;
import com.galimagroup.gestionproduit.service.serviceImp.ProduitServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/produit")
public class ProduitController {
    private final ProduitServiceImpl produitServiceImpl;

    public ProduitController(ProduitServiceImpl produitServiceImpl) {
        this.produitServiceImpl = produitServiceImpl;
    }

    // genrer la methode save produit
    @PostMapping("/creerProduit")
    public ResponseEntity<ProduitDto> createNote(@RequestBody ProduitDto produitDto) throws URISyntaxException {

        ProduitDto createdProduit = produitServiceImpl.saveProduitDto(produitDto);
        return new ResponseEntity<>(createdProduit, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<Page<ProduitDto>> getAllProduits(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ProduitDto> produits = produitServiceImpl.findAllProduit(PageRequest.of(page, size));
        return ResponseEntity.ok(produits);
    }

    // Récupérer un produit par son ID

    @GetMapping("/{id}")
    public ResponseEntity<ProduitDto> getProduitById(@PathVariable Long id) {
        ProduitDto produit = produitServiceImpl.findProduitById(id);
        return ResponseEntity.ok(produit);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProduitDto> updateProduit(
            @PathVariable Long id,
            @RequestBody ProduitDto produitDto
    ) {
        ProduitDto updatedProduit = produitServiceImpl.updateProduit(id, produitDto);
        return ResponseEntity.ok(updatedProduit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        produitServiceImpl.deleteProduit(id);
        return ResponseEntity.ok().build();
    }
}




