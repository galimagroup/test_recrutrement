package com.backend.procuct_backend.controller;

import com.backend.procuct_backend.dto.LoginRequest;
import com.backend.procuct_backend.dto.LoginResponse;
import com.backend.procuct_backend.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.authenticate(loginRequest);
    }
}