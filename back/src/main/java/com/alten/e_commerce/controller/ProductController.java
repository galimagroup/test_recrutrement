package com.alten.e_commerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alten.e_commerce.config.utils.ApiResponse;
import com.alten.e_commerce.dto.ProductDto;
import com.alten.e_commerce.service.ProductService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/products")
public class ProductController {

    final ProductService productService;

    ProductController(ProductService productService){
        this.productService = productService;
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse> save(@RequestBody ProductDto product){
        try{
            return ResponseEntity.ok(new ApiResponse(true, "saved successfully",HttpStatus.CREATED,productService.create(product)));
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(ApiResponse.exceptionResponse(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> index() {
        try {
            return ResponseEntity.ok(new  ApiResponse(true, "Products list",HttpStatus.OK,productService.findAll()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.exceptionResponse(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id) {
        try{
            return ResponseEntity.ok(new ApiResponse(true, "updated successfully",HttpStatus.ACCEPTED,productService.findWithId(id)));
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(ApiResponse.exceptionResponse(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody ProductDto product) {
        try{
            return ResponseEntity.ok(new ApiResponse(true, "updated successfully",HttpStatus.ACCEPTED,productService.update(id,product)));
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(ApiResponse.exceptionResponse(e.getMessage()));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> patch(@PathVariable Long id, @RequestBody ProductDto product) {
        try{
            return ResponseEntity.ok(new ApiResponse(true, "updated successfully",HttpStatus.ACCEPTED,productService.patch(id,product)));
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(ApiResponse.exceptionResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id){
        try{
            productService.delete(id);
            return ResponseEntity.ok(new ApiResponse(true, "deleted successfully"));
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(ApiResponse.exceptionResponse(e.getMessage()));
        } 
    }
    
}
