package com.architech.test.product.products;

import com.architech.test.product.exception.ResourceNotFoundException;
import com.architech.test.product.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Min;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final Tika tika = new Tika();

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Create a new product.", description = "Create a new product.")
    @PostMapping("/")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product) {
        log.info("REST request to add a new product {}", product);
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

    @Operation(summary = "Get a product by id.", description = "Get a product by id.")
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable @Min(1) Long id) {
        log.info("REST request to find a product by id {}", id);
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

    @Operation(summary = "Update a product", description = "Update a product.")
    @PatchMapping("/{id}")
    public ResponseEntity<Response> updateProduct(
        @PathVariable Long id,
        @Validated(UpdateValidationGroup.class) @RequestBody Product product) {
        log.info("REST request to update product with id: {}", id);

        Product updatedProduct = productService.editProduct(id, product);

        return ResponseEntity.ok()
            .body(Response.builder()
                .timeStamp(now())
                .data(Map.of("product", updatedProduct))
                .message("Product updated successfully")
                .status(OK)
                .statusCode(OK.value())
                .build());
    }

    @Operation(summary = "Return all products", description = "Return all products")
    @GetMapping("/")
    public ResponseEntity<Response> getAllProducts() {
        log.info("REST request to get all products.");
        return ResponseEntity.ok().body(
            Response.builder()
                .timeStamp(now())
                .data(productService.getProducts())
                .message("Products retrieved successfully.")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @Operation(summary = "Remove a product", description = "Remove a product.")
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

    @Operation(summary = "Upload product image ", description = "Upload product image")
    @PostMapping(value = "/{id}/upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response> uploadImage(@PathVariable Long id, @RequestPart("file") MultipartFile file) {
        log.info("Uploading image for product: {}", id);

        try {
            Product product = productService.uploadImage(id, file);
            return ResponseEntity.ok()
                .body(Response.builder()
                    .timeStamp(now())
                    .data(Map.of("product", product))
                    .message("Image uploaded successfully")
                    .status(OK)
                    .statusCode(OK.value())
                    .build());
        } catch (ValidationException e) {
            return ResponseEntity.badRequest()
                .body(Response.builder()
                    .timeStamp(now())
                    .message(e.getMessage())
                    .status(BAD_REQUEST)
                    .statusCode(BAD_REQUEST.value())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Response.builder()
                    .timeStamp(now())
                    .message("Failed to upload image: " + e.getMessage())
                    .status(INTERNAL_SERVER_ERROR)
                    .statusCode(INTERNAL_SERVER_ERROR.value())
                    .build());
        }
    }

    @Operation(summary = "Return an image's product.", description = "Return an image's product.")
    @GetMapping("/{productId}/image")
    public ResponseEntity<Resource> getImage(@PathVariable Long productId) {
        log.info("REST request to get image {}", productId);
        try {
            Resource imageResource = productService.getImage(productId);

            // Detect actual content type of the image
            String contentType = tika.detect(imageResource.getInputStream());

            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CACHE_CONTROL, "max-age=31536000") // Cache for 1 year
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + imageResource.getFilename() + "\"")
                .body(imageResource);

        } catch (ResourceNotFoundException e) {
            log.debug("Image not found for product ID: {}", productId);
            return ResponseEntity.notFound().build();

        } catch (ValidationException e) {
            log.warn("Validation error while fetching image for product ID: {}", productId, e);
            return ResponseEntity.badRequest().build();

        } catch (SecurityException e) {
            log.error("Security violation while fetching image for product ID: {}", productId, e);
            return ResponseEntity.badRequest().build();

        } catch (IOException e) {
            log.error("Error reading image for product ID: {}", productId, e);
            return ResponseEntity.internalServerError().build();

        } catch (Exception e) {
            log.error("Unexpected error while fetching image for product ID: {}", productId, e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
