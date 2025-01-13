package com.shop.alten.test_technique.controller;

import com.shop.alten.test_technique.model.User;
import com.shop.alten.test_technique.security.JwtUtil;
import com.shop.alten.test_technique.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User newUser = userService.registerUser(user);
            return ResponseEntity.ok(newUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Vérifier si l'utilisateur existe par email
            User authenticatedUser = userService.findByEmail(user.getEmail());
            if (authenticatedUser == null) {
                response.put("success", false);
                response.put("message", "Email incorrect");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            // Authentifier l'utilisateur (vérification du mot de passe)
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );

            // Générer le token JWT
            String token = jwtUtil.generateToken(user.getEmail());

            // Construire la réponse
            response.put("success", true);
            response.put("message", "Connexion réussie !");
            response.put("token", token);
            response.put("user", authenticatedUser);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Si le mot de passe est incorrect
            response.put("success", false);
            response.put("message", "Mot de passe incorrect");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }




}

