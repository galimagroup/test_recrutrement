package com.backend.procuct_backend.entitie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false, length = 50)
    private String code;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
    private String image;
    private String category;

    @Column(nullable = false)
    private Integer price;

    private int quantity;
    private String internalReference;
    private Long shellId;

    @Enumerated(EnumType.STRING)
    private InventoryStatus inventoryStatus;

    private int rating;

    @Column(updatable = false)
    private Instant createdAt;

    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
        updateInventoryStatus();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
        updateInventoryStatus();
    }

    private void updateInventoryStatus() {
        if (quantity > 100) {
            inventoryStatus = InventoryStatus.INSTOCK;
        } else if (quantity > 0) {
            inventoryStatus = InventoryStatus.LOWSTOCK;
        } else {
            inventoryStatus = InventoryStatus.OUTOFSTOCK;
        }
    }

    public enum InventoryStatus {
        INSTOCK, LOWSTOCK, OUTOFSTOCK
    }
}