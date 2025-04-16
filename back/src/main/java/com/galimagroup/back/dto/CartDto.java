package com.galimagroup.back.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDto {
    private Long id;
    private Long userId; 
    private List<CartItemDto> items;
    private Double total;
}
