package com.galimagroup.gestionproduit.dto;

import com.galimagroup.gestionproduit.entity.Produit;
import com.galimagroup.gestionproduit.entity.enumeration.InventoryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduitDto {
    private String code;
    private String name;
    private String description;
    private String image;
    private String category;
    private double price;
    private Integer quantity;
    private String internalReference;
    private Long shellId;
    private InventoryStatus inventoryStatus;
    private int rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProduitDto(Produit newproduit) {
        this.code = newproduit.getCode();
        this.name = newproduit.getName();
        this.description = newproduit.getDescription();
        this.image = newproduit.getImage();
        this.category = newproduit.getCategory();
        this.price = newproduit.getPrice();
        this.internalReference = newproduit.getInternalReference();
        this.shellId = newproduit.getShellId();
        this.inventoryStatus = newproduit.getInventoryStatus();
        this.rating = newproduit.getRating();
        this.createdAt = newproduit.getCreatedAt();
        this.updatedAt = newproduit.getUpdatedAt();
    }

    public Produit recupererProduit() {
        Produit produit = new Produit();
        produit.setCode(this.code);
        produit.setName(this.name);
        produit.setDescription(this.description);
        produit.setImage(this.image);
        produit.setCategory(this.category);
        produit.setPrice(this.price);
        produit.setQuantity(this.quantity);
        produit.setInternalReference(this.internalReference);
        produit.setShellId(this.shellId);
        produit.setInventoryStatus(this.inventoryStatus);
        produit.setRating(this.rating);
        produit.setCreatedAt(this.createdAt);
        produit.setUpdatedAt(this.updatedAt);
        return produit;
    }
}
