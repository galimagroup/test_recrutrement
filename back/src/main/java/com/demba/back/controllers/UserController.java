package com.demba.back.controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demba.back.payloads.UserRequest;
import com.demba.back.services.UserService;
import com.demba.back.utils.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/account")
    public ResponseEntity<?> createAccount(@Valid @RequestBody UserRequest request){
        return ResponseEntity.ok(
            new ApiResponse(
                true, 
                "New Account sucessfully created", 
                HttpStatus.OK, 
                service.createUser(request)
            )
        );
    }
}
