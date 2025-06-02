package com.architech.test.product.dto;

import lombok.Data;

@Data
public class CreateWishlistRequest {
    private Long userId;
    private String name;
    private String description;
    private Boolean isPublic;
}
