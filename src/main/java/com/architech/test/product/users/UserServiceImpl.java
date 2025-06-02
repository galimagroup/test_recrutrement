package com.architech.test.product.users;

import com.architech.test.product.utils.JwtTokenUtil;
import com.architech.test.product.utils.LoginRequest;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);


    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }


    @Override
    public User addUser(User user) {
        log.info("Request to create a new User: {}", user);

        if (!isValidEmail(user.getEmail())) {
            throw new RuntimeException("Invalid email format");
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    @Override
    public ResponseEntity<?> authenticate(LoginRequest loginRequest) {
        log.info("Request to authenticate a user: {} ", loginRequest);
        try {
                // Try authentication
                Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword())
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);

                User user = userRepository.findByEmail(loginRequest.getEmail());
                if (user == null) {
                    throw new EntityNotFoundException("User not found");
                }

                // Generate Token
                String token = jwtTokenUtil.generateToken(loginRequest.getEmail(), user.getUsername(), user.getFirstname());

                // Return successful authentication response with all required details
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("id", user.getId());
                responseData.put("email", user.getEmail());
                responseData.put("token", token);

                return ResponseEntity.ok(responseData);

            } catch (BadCredentialsException e) {
                log.error("Bad credentials for email: {}", loginRequest.getEmail());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
            }

        }

    }

