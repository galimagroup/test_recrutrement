package com.alten.e_commerce.dto;

import com.alten.e_commerce.entity.Product;
import com.alten.e_commerce.enums.InventoryStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    
    private Long id;

    private String code;
    
    private String name;

    private String description;

    private String image;

    private String category;

    private Integer price;

    private Integer quantity;

    private String intervalReference;

    private Integer shellId;

    private Integer rating;

    private InventoryStatus inventoryStatus;

    public Product toProduct(){
        Product p = Product.builder()
        .id(id)
        .code(code)
        .name(name)
        .description(description)
        .image(image)
        .category(category)
        .price(price)
        .quantity(quantity)
        .intervalReference(intervalReference)
        .shellId(shellId)
        .rating(rating)
        .inventoryStatus(inventoryStatus)
        .build();

        return p;
    } 
}
