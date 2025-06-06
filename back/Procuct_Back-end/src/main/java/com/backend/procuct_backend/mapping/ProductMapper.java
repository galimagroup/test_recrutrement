package com.backend.procuct_backend.mapping;

import com.backend.procuct_backend.dto.Product;
import com.backend.procuct_backend.entitie.ProductEntity;
import org.mapstruct.Mapper;



@Mapper
public interface ProductMapper {
    Product toProduct(ProductEntity productEntity);
    ProductEntity fromProduct(Product product);
}

