package com.galimagroup.gestionproduit.entity;

import com.galimagroup.gestionproduit.entity.enumeration.InventoryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

  private Long  id;
    @Column(nullable = false,unique = true)
    private String code;
    @Column(nullable = false)
   private String name;
    private String description;
    private String image;
    @Column(nullable = false)
    private String  category;
    @Column(nullable = false)
    private double price;
    private Integer quantity;
    private String internalReference;
    private  Long shellId;
    @Enumerated(EnumType.STRING)
    @Column(name = "inventory_status", nullable = false)
    private InventoryStatus inventoryStatus;
    private int rating;
  @Column(name = "create_at", updatable = false)
    private LocalDateTime createdAt;
  @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
