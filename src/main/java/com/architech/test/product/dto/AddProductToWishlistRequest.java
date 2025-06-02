package com.architech.test.product.dto;

import lombok.Data;

@Data
public class AddProductToWishlistRequest {
    private Long productId;
    private String note;
    private Integer priority;
}
