package com.demba.back.mappers;

import org.springframework.stereotype.Service;

import com.demba.back.models.Product;
import com.demba.back.payloads.CreateProductRequest;
import com.demba.back.payloads.ProductDto;
import com.demba.back.payloads.ProductResponse;
import com.demba.back.payloads.UpdateProductRequest;

@Service
public class ProductMapper {    

    public Product toCreatingProduct(CreateProductRequest request) {
        return Product.builder()
            .code(request.getCode())
            .name(request.getName())
            .description(request.getDescription())
            .category(request.getCategory())
            .price(request.getPrice())
            .build();
    }    

    public Product toUpdatingProduct(UpdateProductRequest request) {
        return Product.builder()
            .id(request.getId())
            .code(request.getCode())
            .name(request.getName())
            .description(request.getDescription())
            .image(request.getImage())
            .category(request.getCategory())
            .price(request.getPrice())
            .quantity(request.getQuantity())
            .inventoryStatus(request.getInventoryStatus())
            .rating(request.getRating())
            .build();
    }

    public ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
            .id(product.getId())
            .code(product.getCode())
            .name(product.getName())
            .description(product.getDescription())
            .image(product.getImage())
            .category(product.getCategory())
            .price(product.getPrice())
            .quantity(product.getQuantity())
            .internalReference(product.getInternalReference())
            .inventoryStatus(product.getInventoryStatus())
            .rating(product.getRating())
            .createdAt(product.getCreatedAt())
            .updatedAt(product.getUpdatedAt())
            .build();
    }

    public Product toProduct(ProductDto dto) {
        return Product.builder()
            .id(dto.getId())
            .code(dto.getCode())
            .name(dto.getName())
            .description(dto.getDescription())
            .image(dto.getImage())
            .category(dto.getCategory())
            .price(dto.getPrice())
            .quantity(dto.getQuantity())
            .inventoryStatus(dto.getInventoryStatus())
            .rating(dto.getRating())
            .build();
    }

}
