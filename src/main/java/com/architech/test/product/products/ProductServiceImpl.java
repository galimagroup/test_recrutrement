package com.architech.test.product.products;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        log.info("Request to save a new product {}", product);
        product.setCreatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) {
        log.info("Request to find a product by id {}", id);
        return productRepository.findById(id).get();
    }

    @Override
    public void deleteProduct(Long id) {
        log.info("Request to delete a product: {}", id);
        productRepository.deleteById(id);
    }

    @Override
    public Product editProduct(Long id, Product product) {
        log.info("Request to update a product with Id: {}", id);
        productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        return productRepository.save(product);
    }

    @Override
    public List<Product> getProducts() {
        log.info("Request to get all products");
        return productRepository.findAll();
    }
}
