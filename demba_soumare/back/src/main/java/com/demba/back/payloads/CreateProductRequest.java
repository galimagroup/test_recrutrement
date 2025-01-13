package com.demba.back.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {
    private String code;
    private String name;
    private String description;
    private String category;
    private double price;
}
