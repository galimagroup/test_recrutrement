package com.galimagroup.back.service;

import com.galimagroup.back.model.Users;
import com.galimagroup.back.repository.UserRepository;
import com.galimagroup.back.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    // Enregistrement d'un utilisateur
    public String registerUser(Users user) {
        // Vérifier si l'email est déjà utilisé
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé !");
        }

        // Encoder le mot de passe
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Sauvegarder l'utilisateur dans la base de données
        userRepository.save(user);

        // Générer un token JWT pour l'utilisateur
        return tokenProvider.generateToken(user.getEmail());
    }

    // Récupérer un utilisateur par email
    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));
    }

    // Vérification du mot de passe de l'utilisateur
    public boolean checkPassword(Users user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }
}
