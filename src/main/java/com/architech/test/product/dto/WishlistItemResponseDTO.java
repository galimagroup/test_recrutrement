package com.architech.test.product.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class WishlistItemResponseDTO {
    private Long id;
    private String note;
    private Integer priority;
    private LocalDateTime addedAt;
    private ProductSummaryDTO product;
}
