package com.alten.e_commerce.interfaces;

import java.util.List;

import com.alten.e_commerce.dto.ProductDto;
import com.alten.e_commerce.entity.Product;

public interface IProduct {
    
    Product create(ProductDto productdDto);

    List<Product> findAll();

    Product findWithId(Long id);

    Product update(Long id,ProductDto productdDto);

    void delete(Long id);

    Product patch(Long id,ProductDto productdDto);
}
