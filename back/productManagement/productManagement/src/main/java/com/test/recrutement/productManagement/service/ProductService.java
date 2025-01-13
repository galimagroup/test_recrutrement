package com.test.recrutement.productManagement.service;

import com.test.recrutement.productManagement.model.Product;
import com.test.recrutement.productManagement.repo.ProductRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product addProduct (Product product) {
        validateAdminAccess();
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        validateAdminAccess();
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(product.getName() != null ? product.getName() : existingProduct.getName());
                    existingProduct.setDescription(product.getDescription() != null ? product.getDescription() : existingProduct.getDescription());
                    existingProduct.setPrice(product.getPrice() != null ? product.getPrice() : existingProduct.getPrice());
                    existingProduct.setQuantity(product.getQuantity() != null ? product.getQuantity() : existingProduct.getQuantity());
                    existingProduct.setCategory(product.getCategory() != null ? product.getCategory() : existingProduct.getCategory());
                    existingProduct.setImage(product.getImage() != null ? product.getImage() : existingProduct.getImage());
                    existingProduct.setInventoryStatus(product.getInventoryStatus() != null ? product.getInventoryStatus() : existingProduct.getInventoryStatus());
                    existingProduct.setRating(product.getRating() != null ? product.getRating() : existingProduct.getRating());


                    return productRepository.save(existingProduct);
                })
                .orElseThrow(() -> new RuntimeException("Produit avec l'ID " + id + " non trouvé"));
    }

    public void deleteProduct(Long id) {
        validateAdminAccess();
        productRepository.deleteById(id);
    }

    private void validateAdminAccess() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            throw new RuntimeException("Unauthorized access");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername(); // Le username correspond à l'email
        if (!"admin@admin.com".equals(email)) {
            throw new RuntimeException("Access denied: Only admin can perform this operation");
        }
    }


}
