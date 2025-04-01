package sow.issa.recrutement.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sow.issa.recrutement.models.request.ProductRequest;
import sow.issa.recrutement.models.response.ProductResponse;
import sow.issa.recrutement.services.ProductService;

import java.util.Map;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

//    @Operation(summary = "sign in by login and password", description = "dhdg")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Success"),
//            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
//            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity.ok(productService.createProduct(productRequest));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> readAllProducts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.readAllProducts(page, size));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public  ResponseEntity<ProductResponse> readProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.readProduct(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public  ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.updateProduct(id, productRequest));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeProduct(@PathVariable Long id) {
        productService.removeProduct(id);
        return ResponseEntity.ok().body("Suppression réussi");
    }
}
