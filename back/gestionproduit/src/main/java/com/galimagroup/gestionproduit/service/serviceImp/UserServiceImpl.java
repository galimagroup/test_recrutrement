package com.galimagroup.gestionproduit.service.serviceImp;

import com.galimagroup.gestionproduit.dto.AuthReponseDto;
import com.galimagroup.gestionproduit.dto.LoginDto;
import com.galimagroup.gestionproduit.dto.RegisterDto;
import com.galimagroup.gestionproduit.entity.User;
import com.galimagroup.gestionproduit.repo.UtilisateurRepository;
import com.galimagroup.gestionproduit.security.jwt.JWTUtil;
import com.galimagroup.gestionproduit.service.serviceInterface.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private  final UtilisateurRepository utilisateurRepository;

    private final PasswordEncoder passwordEncoder; // Changez ici
    private final JWTUtil jwtUtil;

    public UserServiceImpl(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }



    @Override
    public RegisterDto saveUser(RegisterDto registerDto) {
        // Vérifier si un utilisateur existe déjà avec le même email
        if (utilisateurRepository.existsByEmail(registerDto.getEmail())) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà");
        }

        // Encoder le mot de passe
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());

        // Créer un nouvel utilisateur
        User user = new User(
                registerDto.getUsername(),
                registerDto.getFirstname(),
                registerDto.getEmail(),
                encodedPassword
        );
        utilisateurRepository.save(user);
        return  registerDto;    }

    @Override
    public User findUserByEmail(String email) {
        return utilisateurRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    @Override
    public AuthReponseDto login(LoginDto loginDTO) {
        // Vérifier si l'utilisateur existe avec l'email fourni
        User user = findUserByEmail(loginDTO.getEmail());

        // Vérifier le mot de passe
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        // Générer un token JWT
        String token = JWTUtil.generateToken(user.getEmail());

        return new AuthReponseDto(token);
    }
    // Vérifier si l'utilisateur est l'admin
    public boolean isAdmin(User user) {
        return "admin@admin.com".equals(user.getEmail());
    }
    }

