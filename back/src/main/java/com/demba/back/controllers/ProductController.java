package com.demba.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demba.back.models.Product;
import com.demba.back.payloads.CreateProductRequest;
import com.demba.back.payloads.UpdateProductRequest;
import com.demba.back.services.ProductService;
import com.demba.back.utils.ApiResponse;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;    

    @PostMapping
    public ResponseEntity<?> createProduct(
        @Valid @RequestBody CreateProductRequest request,
        Authentication connectedUser
    ) {
        try {
            return ResponseEntity.ok(ApiResponse.successResponse(
                "Product created successfully", 
                HttpStatus.CREATED, 
                productService.create(request, connectedUser)
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> list() {
        return ResponseEntity.ok(ApiResponse.successResponse("List of all products",  HttpStatus.OK, productService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findProductById(
        @PathVariable("id") Long productId
    ) {
        try {
            return ResponseEntity.ok(ApiResponse.successResponse(
                "Product found successfully", 
                HttpStatus.OK, 
                productService.findById(productId)
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.errorResponse(e.getMessage(), HttpStatus.NOT_FOUND));
        }
    }
    
    @PutMapping("/{id}")
     public ResponseEntity<?> updateProduct(
        @PathVariable("id") Long productId, 
        @RequestBody @Valid UpdateProductRequest request,
        Authentication connectedUser
        ) {
        try {
            return ResponseEntity.ok(ApiResponse.successResponse(
                "Product updated successfully", 
                HttpStatus.OK, 
                productService.update(productId, request, connectedUser)
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }

    @DeleteMapping("/{id}")
     public ResponseEntity<?> deleteProduct(@PathVariable("id") Long productId, Authentication connectedUser) {
        try {
            return ResponseEntity.ok(productService.delete(productId, connectedUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.errorResponse(e.getMessage(), HttpStatus.NOT_FOUND));
        }
    }

    //Mettre à jour l'image d'un produit
    @PostMapping(value = "/image/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadProductImage(
        @PathVariable("id") Long id,
        @Parameter()
        @RequestPart("file") MultipartFile file
    ) {
        try {
            Product product = productService.changeImage(file, id);
            return ResponseEntity.ok(ApiResponse.successResponse("Product image uploaded successfully", HttpStatus.OK, product));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }    

    //Ajouter un produit à la liste d'envie
    @PostMapping("/desired/add/{id}")
    public ResponseEntity<?> addUserDesiredProduct(
        @PathVariable("id") Long id,
        Authentication connectedUser
    ) {
        try {
            productService.addDesiredProduct(id, connectedUser);
            return ResponseEntity.ok(ApiResponse.successResponse(
                "Product successfully added to user favorites.", 
                HttpStatus.OK
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST));
        }
    } 

    //Retirer un produit de la liste d'envie
    @PostMapping("/desired/remove/{id}")
    public ResponseEntity<?> removeUserDesiredProduct(
        @PathVariable("id") Long id,
        Authentication connectedUser
    ) {
        try {
            productService.removeDesiredProduct(id, connectedUser);
            return ResponseEntity.ok(ApiResponse.successResponse(
                "Product successfully removed from user favorites.", 
                HttpStatus.OK
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }

}
