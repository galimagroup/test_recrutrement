package com.galimagroup.back.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super("Produit non trouvé");
    }

    public ProductNotFoundException(Long id) {
        super("Produit avec l'id " + id + " non trouvé");
    }
}
