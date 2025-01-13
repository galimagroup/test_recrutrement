package com.shop.alten.test_technique.service;


import com.shop.alten.test_technique.model.Product;
import com.shop.alten.test_technique.repository.ProductRepository;
import com.shop.alten.test_technique.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'id : " + id));

        existingProduct.setName(product.getName());
        existingProduct.setCode(product.getCode());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setInventoryStatus(product.getInventoryStatus());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setRating(product.getRating());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setImage(product.getImage());
        existingProduct.setInternalReference(product.getInternalReference());
        existingProduct.setShellId(product.getShellId());
        return productRepository.save(existingProduct);
    }


    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
    }

    @Override
    public Page<Product> getFilteredProducts(Pageable pageable, String category, Double minPrice, Double maxPrice, String keyword) {
        return productRepository.findByFilters(category, minPrice, maxPrice, keyword, pageable);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public void deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
        productRepository.delete(existingProduct);
    }
}

