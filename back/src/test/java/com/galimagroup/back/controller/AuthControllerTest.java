package com.galimagroup.back.controller;

import com.galimagroup.back.model.Users;
import com.galimagroup.back.repository.UserRepository;
import com.galimagroup.back.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider tokenProvider;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testRegisterUser_Success() throws Exception {
        Users user = new Users();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userRepository.findByEmail(any())).thenReturn(java.util.Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any(Users.class))).thenReturn(user);
        when(tokenProvider.generateToken(any())).thenReturn("mockJwtToken");

        mockMvc.perform(post("/api/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@example.com\", \"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mockJwtToken"));
    }

    @Test
    public void testRegisterUser_EmailAlreadyExists() throws Exception {
        Users existingUser = new Users();
        existingUser.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(java.util.Optional.of(existingUser));

        mockMvc.perform(post("/api/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@example.com\", \"password\":\"password\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email déjà utilisé !"));
    }

    @Test
    public void testAuthenticateUser_Success() throws Exception {
        when(authenticationManager.authenticate(any())).thenReturn(null); 
        when(tokenProvider.generateToken(any())).thenReturn("mockJwtToken");

        mockMvc.perform(post("/api/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@example.com\", \"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mockJwtToken"));
    }

    @Test
    public void testAuthenticateUser_InvalidCredentials() throws Exception {
        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException("Invalid credentials"));

        mockMvc.perform(post("/api/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"wrong@example.com\", \"password\":\"wrongpassword\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Identifiants invalides !"));
    }
}
