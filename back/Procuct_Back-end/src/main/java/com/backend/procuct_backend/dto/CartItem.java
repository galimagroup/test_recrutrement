package com.backend.procuct_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private int id;
    private Product product;
    private Integer quantity;
    private double subtotal;
}
