package com.architech.test.product.dto;

import com.architech.test.product.enums.InventoryStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductSummaryDTO {
    private Long id;
    private String code;
    private String name;
    private String image;
    private Integer price;
    private String category;
    private InventoryStatus inventoryStatus;
}
