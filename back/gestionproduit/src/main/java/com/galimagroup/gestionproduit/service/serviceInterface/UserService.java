package com.galimagroup.gestionproduit.service.serviceInterface;

import com.galimagroup.gestionproduit.dto.AuthReponseDto;
import com.galimagroup.gestionproduit.dto.LoginDto;
import com.galimagroup.gestionproduit.dto.RegisterDto;
import com.galimagroup.gestionproduit.entity.User;

public interface UserService {
    // Méthode pour enregistrer un nouvel utilisateur
    RegisterDto saveUser(RegisterDto registerDto);

    // Méthode pour trouver un utilisateur par email
    User findUserByEmail(String email);

    // Méthode pour générer un JWT pour un utilisateur lors de la connexion
    AuthReponseDto login(LoginDto loginDTO);
}
