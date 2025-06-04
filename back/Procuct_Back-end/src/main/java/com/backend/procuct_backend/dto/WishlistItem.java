package com.backend.procuct_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistItem {
    private int id;
    private Product product;
    private LocalDateTime addedAt;
}
