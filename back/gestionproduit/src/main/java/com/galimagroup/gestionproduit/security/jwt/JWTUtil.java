package com.galimagroup.gestionproduit.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtil {

    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
            Decoders.BASE64.decode("N2JmY2IzMDM2ZTg2OTU3YzlmMzU2MmJlMjA3M2U2Y2EwM2JmY2M1MzFkNzJlYzY5M2E5NjM5NDc3YTEwZDhhOTk3YzAwNTc4MjIzYzEzZjAxMDI0YTg4YTEzYTFjZjlkMDc2M2FhYjgzYjgyNTVkNmVmNTM5Y2U5NDM1ZWMwYjE=")
    );
    private static final long EXPIRATION_TIME = 864000000L; // 10 jours en millisecondes

    // Génère un token JWT
    public static String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY) // Utilise un SecretKey sécurisé
                .compact();
    }

    // Extrait le username du token
    public static String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // Utilise le même SecretKey pour valider le token
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Valide le token JWT
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token); // Essaie de parser le token
            return true; // Si le token est valide
        } catch (ExpiredJwtException e) {
            System.out.println("Token expiré");

        } catch (SignatureException e) {
            System.out.println("Signature invalide");
        } catch (MalformedJwtException e) {
            System.out.println("Token malformé");
        } catch (Exception e) {
            System.out.println("Token invalide");
        }
        return false; // Si une exception est lancée, cela signifie que le token est invalide
    }
}
