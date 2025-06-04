package com.architech.test.product.products;

import com.architech.test.product.exception.ResourceNotFoundException;
import com.architech.test.product.utils.LoginRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface ProductService {

    Product createProduct(Product product);
    Product findProductById(Long id);
    void deleteProduct(Long id);
    Product editProduct(Long id, Product product);
    List<Product> getProducts();
    Product uploadImage(Long userId, MultipartFile file);
    Resource getImage(Long userId) throws ResourceNotFoundException;
}
