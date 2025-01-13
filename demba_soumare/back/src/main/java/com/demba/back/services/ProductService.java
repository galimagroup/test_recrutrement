package com.demba.back.services;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demba.back.mappers.ProductMapper;
import com.demba.back.models.Product;
import com.demba.back.models.User;
import com.demba.back.payloads.CreateProductRequest;
import com.demba.back.payloads.ProductResponse;
import com.demba.back.payloads.UpdateProductRequest;
import com.demba.back.repositories.ProductRepository;
import com.demba.back.repositories.UserRepository;

@Service
public class ProductService { 

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ProductMapper productMapper;

    public ProductResponse create(CreateProductRequest request, Authentication connectedUser) {
        assertAdminUser(connectedUser);
        Product product = productMapper.toCreatingProduct(request);
        product.setInternalReference(generateRef(20));
        return productMapper.toProductResponse(productRepository.save(product));
    }

    public List<ProductResponse> getAll() {
        return productRepository
            .findAll().stream()
            .map(productMapper::toProductResponse)
            .collect(Collectors.toList()) ;
    }

    public ProductResponse findById(Long productId) {
        return productMapper.toProductResponse(retreiveById(productId));
    }

    public ProductResponse update(Long productId, UpdateProductRequest request, Authentication connectedUser) {
        assertAdminUser(connectedUser);
        Product product = productMapper.toUpdatingProduct(request);
        product.setId(retreiveById(productId).getId());
        return productMapper.toProductResponse(productRepository.save(product));
    }

    public Boolean delete(Long productId, Authentication connectedUser) throws IOException {
        assertAdminUser(connectedUser);
        Product product = retreiveById(productId);
        String image = product.getImage();
        if (image != null) {
            fileStorageService.removeFile(image);
        }
        productRepository.deleteById(product.getId());
        return true;
    }

    public Product changeImage(MultipartFile file, Long productId) throws IOException {
        Product product = retreiveById(productId);
        var image = fileStorageService.saveProductImage(file);
        String oldImage = product.getImage();
        product.setImage(image);
        if (oldImage != null) {
            fileStorageService.removeFile(oldImage);
        }
        return productRepository.save(product);
    }

    public Boolean addDesiredProduct(Long productId, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Product product = retreiveById(productId);
        List<Product> products = user.getDesiredProducts();
        products.add(product);
        user.setDesiredProducts(products);
        userRepository.save(user);
        return true;
    }

    public Boolean removeDesiredProduct(Long productId,  Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        productRepository.deleteDesiredProduct(productId, user.getId());
        return true;
    }

    private Product retreiveById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No product found with ID:: " + id));
    }

    private void assertAdminUser(Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        if (!user.getEmail().equals("admin@admin.com")) {
            throw new RuntimeException("You have not rights for this process!");
        }
    }

    private String generateRef(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }
}
