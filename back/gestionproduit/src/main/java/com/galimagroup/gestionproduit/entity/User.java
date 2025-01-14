package com.galimagroup.gestionproduit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    private  String username;
    private String firstname ;
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
    // Constructeur personnalisé pour initialiser un utilisateur avec les paramètres spécifiés
    public User(String username, String firstname, String email, String encodedPassword) {
        this.username = username;
        this.firstname = firstname;
        this.email = email;
        this.password = encodedPassword;
    }
}
