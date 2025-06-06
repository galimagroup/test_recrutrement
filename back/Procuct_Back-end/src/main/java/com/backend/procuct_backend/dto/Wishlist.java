package com.backend.procuct_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wishlist {
    private int id;
    private int userId;
    private List<WishlistItem> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
