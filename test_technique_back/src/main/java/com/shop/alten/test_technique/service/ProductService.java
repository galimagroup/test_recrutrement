package com.shop.alten.test_technique.service;

import com.shop.alten.test_technique.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);

    Product updateProduct(Long id, Product product);

    Product getProductById(Long id);

    List<Product> getAllProducts();

    Page<Product> getProducts(Pageable pageable);

    void deleteProduct(Long id);

    Page<Product> getFilteredProducts(Pageable pageable, String category, Double minPrice, Double maxPrice, String keyword);
}
