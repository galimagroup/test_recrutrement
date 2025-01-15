package com.alten.e_commerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.alten.e_commerce.config.utils.ApiResponse;
import com.alten.e_commerce.entity.ShoppingCart;
import com.alten.e_commerce.service.ShoppingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/products")
public class ShoppingController {
    
    private final ShoppingService shoppingService;

    ShoppingController(ShoppingService shoppingService){
        this.shoppingService = shoppingService;

    }

    @GetMapping("/like")
    public ResponseEntity<?> favorites() {
        try {
            return ResponseEntity.ok(new ApiResponse(true, "list",HttpStatus.OK,shoppingService.myFavoriteProducts()));
        } catch (Exception e) {
           return ResponseEntity.badRequest().body(ApiResponse.exceptionResponse(e.getMessage()));
        }
    }
    

    @GetMapping("/like/{id}")
    public  ResponseEntity<?>  like(@PathVariable Long id) {
        shoppingService.like(id);
        return ResponseEntity.ok(new ApiResponse(true, "saved successfully"));
    }

    @GetMapping("/dislike/{id}")
    public  ResponseEntity<?>  dislike(@PathVariable Long id) {
        shoppingService.dislike(id);
        return ResponseEntity.ok(new ApiResponse(true, "saved successfully"));
    }

    @GetMapping("/cart")
    public ResponseEntity<?> myCart() {
        try {
            return ResponseEntity.ok(new ApiResponse(true, "list",HttpStatus.OK,shoppingService.myCarts()));
        } catch (Exception e) {
           return ResponseEntity.badRequest().body(ApiResponse.exceptionResponse(e.getMessage()));
        }
    }

    @PostMapping("/cart/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody ShoppingCart shoppingCart) {
        try {
            return ResponseEntity.ok(new ApiResponse(true, "Saved successfully",HttpStatus.CREATED,shoppingService.addToCart(shoppingCart)));
        } catch (Exception e) {
           return ResponseEntity.badRequest().body(ApiResponse.exceptionResponse(e.getMessage()));
        }
    }

    @PostMapping("/cart/remove")
    public ResponseEntity<ApiResponse> removeToCart(@RequestBody ShoppingCart shoppingCart) {
        try {
            shoppingService.removeToCart(shoppingCart);
            return ResponseEntity.ok(new ApiResponse(true, "Saved successfully"));
        } catch (Exception e) {
           return ResponseEntity.badRequest().body(ApiResponse.exceptionResponse(e.getMessage()));
        }
    }
   
}
