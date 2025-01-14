package com.galimagroup.back.service;

import com.galimagroup.back.entities.Product;

import java.util.List;

public interface ProductService {
    public List<Product> getAllProducts();
    public Product getProductById(String id);
    public Product createProduct(Product product);
    public Product updateProduct(String id, Product updatedProduct);
    public void deleteProduct(String id);
}
