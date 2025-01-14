package com.demba.back.payloads;

import java.time.Instant;

import com.demba.back.enums.InventoryStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;

    private String code;
    private String name;
    private String description;
    private String image;
    private String category;
    private double price;
    private int quantity;
    private String internalReference;
    private InventoryStatus inventoryStatus;
    private int rating;
    private Instant createdAt;
    private Instant updatedAt;
}
