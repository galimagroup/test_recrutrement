package com.architech.test.product.products;

import com.architech.test.product.utils.Response;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product) {
        log.debug("REST request to add a new product {}", product);
        return ResponseEntity.status(CREATED).body(
            Response.builder()
                .timeStamp(now())
                .data(productService.createProduct(product))
                .message("Product created successfully.")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable @Min(1) Long id) {
        log.debug("REST request to find a product by id {}", id);
        return ResponseEntity.ok().body(
            Response.builder()
                .timeStamp(now())
                .data(productService.findProductById(id))
                .message("Product is getting successfully.")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        log.debug("REST request to update a new product {}", product);
        return ResponseEntity.ok().body(
            Response.builder()
                .timeStamp(now())
                .data(productService.editProduct(id, product))
                .message("Product updated successfully.")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @GetMapping("/")
    public ResponseEntity<Response> getAllProducts() {
        log.debug("REST request to get all products.");
        return ResponseEntity.ok().body(
            Response.builder()
                .timeStamp(now())
                .data(productService.getProducts())
                .message("Product retrieved successfully.")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeProduct(@PathVariable Long id) {
        log.info("REST request to delete a product with ID: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.ok().body(
            Response.builder()
                .timeStamp(now())
                .message("Product deleted successfully.")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }
}
