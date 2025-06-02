package com.architech.test.product.products;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product);
    Product findProductById(Long id);
    void deleteProduct(Long id);
    Product editProduct(Long id, Product product);
    List<Product> getProducts();
}
