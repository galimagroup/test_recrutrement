package com.backend.procuct_backend.dto;

import com.backend.procuct_backend.entitie.ProductEntity;
import lombok.Data;

import java.time.Instant;

@Data
public class Product {
    private int id;
    private String code;
    private String name;
    private String description;
    private String image;
    private String category;
    private Integer price;
    private int quantity;
    private String internalReference;
    private Long shellId;
    private ProductEntity.InventoryStatus inventoryStatus;
    private int rating;
    private Instant createdAt;
    private Instant updatedAt;
    private Integer userId;
}