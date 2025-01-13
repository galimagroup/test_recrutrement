package com.demba.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demba.back.payloads.OrderRequest;
import com.demba.back.services.OrderService;
import com.demba.back.utils.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //Création d'une commande qui représente la liste des produit du panier
    @PostMapping
    public ResponseEntity<?> createOrder(
        @Valid @RequestBody OrderRequest request,
        Authentication connectedUser
    ) {
        try {
            return ResponseEntity.ok(ApiResponse.successResponse(
                "Order created successfully", 
                HttpStatus.CREATED, 
                orderService.create(request, connectedUser)
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
}
