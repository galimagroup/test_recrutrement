package com.backend.procuct_backend.service;

import com.backend.procuct_backend.dao.IUserRepository;
import com.backend.procuct_backend.dto.LoginRequest;
import com.backend.procuct_backend.dto.LoginResponse;
import com.backend.procuct_backend.entitie.UserEntity;
import com.backend.procuct_backend.exception.EntityNotFoundException;
import com.backend.procuct_backend.exception.RequestException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse authenticate(LoginRequest loginRequest) {
        UserEntity userEntity = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RequestException("Email ou mot de passe incorrect", HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword())) {
            throw new RequestException("Email ou mot de passe incorrect", HttpStatus.UNAUTHORIZED);
        }

        String token = jwtService.generateToken(userEntity);
        Long expiresIn = jwtService.getExpirationTime();

        return new LoginResponse(
                token,
                "Bearer",
                expiresIn,
                userEntity.getEmail(),
                userEntity.getUsername()
        );
    }
}