package com.galimagroup.Backend.TestRecrutement.controller;

import com.galimagroup.Backend.TestRecrutement.dto.UserLoginRequest;
import com.galimagroup.Backend.TestRecrutement.dto.UserRegistrationRequest;
import com.galimagroup.Backend.TestRecrutement.entity.User;
import com.galimagroup.Backend.TestRecrutement.service.AuthService;
import com.galimagroup.Backend.TestRecrutement.util.JwtUtil;
import com.galimagroup.Backend.TestRecrutement.util.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/account")
    public String register(@RequestBody UserRegistrationRequest user) {
        authService.registerUser(user);
        return "User registered successfully";
    }

    @PostMapping("/token")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest user) {
        System.out.println();
        System.out.println();
        System.out.println(user.getEmail());
        System.out.println();System.out.println();

        User authenticatedUser = authService.loginUser(user.getEmail(), user.getPassword());
        if (authenticatedUser != null) {
            System.out.println(authenticatedUser.getEmail());
            String token = jwtUtil.generateToken(authenticatedUser.getEmail());
            return ResponseEntity.ok("Bearer " + token);
        }
        return ResponseEntity.badRequest().body("Invalid credentials");
    }



}
