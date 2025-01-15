package com.galimagroup.Backend.TestRecrutement.service;


import com.galimagroup.Backend.TestRecrutement.dto.UserRegistrationRequest;
import com.galimagroup.Backend.TestRecrutement.entity.User;
import com.galimagroup.Backend.TestRecrutement.exception.ErrorResponse;
import com.galimagroup.Backend.TestRecrutement.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthRepository authRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    public User registerUser(UserRegistrationRequest user) {
            User us = new User();
            us.setEmail(user.getEmail());
            us.setUsername(user.getUsername());
            us.setFirstname(user.getFirstName());
            us.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return authRepository.save(us);

    }

    public User loginUser(String email, String password) {
        System.out.println(email);
        User user = authRepository.findByEmail(email);
        if (user != null && bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }


}
