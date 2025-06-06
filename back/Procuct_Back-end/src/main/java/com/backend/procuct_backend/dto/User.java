package com.backend.procuct_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    @NotNull (message = "Le username ne doit pas etre null")
    private String username;
    @NotNull(message = "Le firstname ne doit pas etre null")
    private String firstname;
    @NotNull(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;
    @NotNull(message = "Le password ne doit pas etre null")
    private String password;

}

