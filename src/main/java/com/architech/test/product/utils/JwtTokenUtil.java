package com.architech.test.product.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration:3600000}") // 1 hour in milliseconds
    private Long expiration;

    @Value("${jwt.refresh.expiration:86400000}") // 24 hours in milliseconds
    private Long refreshExpiration;

    // Extract username from token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Extract expiration date from token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // Extract claim from token
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from token
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    // Check if token is expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Generate token
    public String generateToken(String email, String username, String firstname) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("firstname", firstname);
        claims.put("username", username);
        return doGenerateToken(claims, email);
    }

    // Generate token with claims and subject
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }

    // Generate refresh token
    public String generateRefreshToken(String email) {
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }

    // Validate token
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = getUsernameFromToken(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        try {
            // Assuming you're using io.jsonwebtoken library (JJWT)
            Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            // Token has expired
            throw new RuntimeException("JWT token has expired", e);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            // Invalid token
            throw new RuntimeException("Invalid JWT token", e);
        }
    }
}
