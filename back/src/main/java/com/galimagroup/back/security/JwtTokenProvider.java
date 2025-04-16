package com.galimagroup.back.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    // Injection de la clé secrète depuis application.properties
    @Value("${jwt.secret}")
    private String secretKey;

    // Générer un token
    public String generateToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 86400000); // Expiration dans 24 heures

        Algorithm algorithm = Algorithm.HMAC512(secretKey);

        return JWT.create()
                .withSubject(email)
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .sign(algorithm);
    }

    // Valider un token
    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Extraire l'email du token
    public String getEmailFromJWT(String token) {
        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }

    // Vérifie si l'utilisateur est admin en fonction de son email
    public boolean isAdmin(String token) {
        String email = getEmailFromJWT(token);
        return "admin@admin.com".equalsIgnoreCase(email);
    }
}
