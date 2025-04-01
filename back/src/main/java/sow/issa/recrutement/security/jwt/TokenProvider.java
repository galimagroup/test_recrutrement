package sow.issa.recrutement.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import sow.issa.recrutement.properties.JwtProperties;
import sow.issa.recrutement.repositories.UserRepository;
import sow.issa.recrutement.utils.HttpRequestResponseUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String USER_AGENT = "user_agent";

    private final Key key;

    private final JwtParser jwtParser;

    private final long tokenValidityInMilliseconds;

    private final long tokenValidityInMillisecondsForRememberMe;
    static final String ACCOUNT_LOCKED = "Ce compte est bloqué: {0}";
    private final UserRepository userRepository;

    public TokenProvider(JwtProperties jwtProperties, UserRepository userRepository) {
        this.userRepository = userRepository;
        byte[] keyBytes;
        String secret = jwtProperties.getBase64Secret();
        if (!ObjectUtils.isEmpty(secret)) {
            log.debug("Using a Base64-encoded JWT secret key");
            keyBytes = Decoders.BASE64.decode(secret);
        } else {
            log.warn(
                    "Warning: the JWT key used is not Base64-encoded. " +
                            "We recommend using the `jwt.base64-secret` key for optimum security."
            );
            secret = jwtProperties.getSecret();
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        }
        key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        this.tokenValidityInMilliseconds = 1000 * jwtProperties.getTokenValidityInSeconds();
        this.tokenValidityInMillisecondsForRememberMe =
                1000 * jwtProperties.getTokenValidityInSecondsForRememberMe();
    }

    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }

        return Jwts
                .builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .claim(USER_AGENT, HttpRequestResponseUtils.getUserAgent())
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        String userAgent = (String) claims.get(USER_AGENT);

        if (Objects.isNull(userAgent) || !userAgent.equals(HttpRequestResponseUtils.getUserAgent())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            jwtParser.parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException e) {
            log.trace("Invalid JWT token.", e);
        } catch (UnsupportedJwtException e) {
            log.trace("Invalid JWT token.", e);
        } catch (MalformedJwtException e) {
            log.trace("Invalid JWT token.", e);
        } catch (SignatureException e) {
            log.trace("Invalid JWT token.", e);
        } catch (IllegalArgumentException e) {
            log.error("Token validation error {}", e.getMessage());
        }

        return false;
    }
}
