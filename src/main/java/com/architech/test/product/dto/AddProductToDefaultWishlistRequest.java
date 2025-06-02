package com.architech.test.product.dto;

import lombok.Data;

@Data
public class AddProductToDefaultWishlistRequest {
    private Long userId;
    private Long productId;
    private String note;
}
