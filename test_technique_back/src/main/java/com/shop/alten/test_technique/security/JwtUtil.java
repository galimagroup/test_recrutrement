package com.shop.alten.test_technique.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key SECRET_KEY = Keys.hmacShaKeyFor("alten_scret_key_for_test_technique".getBytes());


    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // Expire dans 1 heure
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // Utilisation de la clé
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired");
            return false;
        } catch (MalformedJwtException e) {
            System.out.println("Malformed token");
            return false;
        } catch (SignatureException e) {
            System.out.println("Invalid signature");
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Token is empty or null");
            return false;
        }
    }
}
