package com.architech.test.product.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
public class WishlistResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Boolean isDefault;
    private Boolean isPublic;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer itemCount;
    private List<WishlistItemResponseDTO> items;
}
