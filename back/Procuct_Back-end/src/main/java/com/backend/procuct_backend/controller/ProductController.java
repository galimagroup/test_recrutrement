package com.backend.procuct_backend.controller;

import com.backend.procuct_backend.dto.Product;
import com.backend.procuct_backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<Product> getAppProducts() {
        return productService.getProduct();
    }

    @GetMapping("/{id}")
    public Product getProductId(@PathVariable("id") int id) {
        return productService.getProductId(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Product createProduct(@Valid @RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PatchMapping("/{id}")
    public Product patchProduct(@PathVariable("id") int id, @RequestBody Product product) {
        return productService.patchProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
    }
}