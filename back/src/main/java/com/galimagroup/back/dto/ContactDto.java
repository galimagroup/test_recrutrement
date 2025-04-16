package com.galimagroup.back.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContactDto {
    private Long id;

    @Email(message = "L'adresse email n'est pas valide")
    @NotBlank(message = "L'email est requis")
    private String email;

    @NotBlank(message = "Le message est requis")
    @Size(max = 300, message = "Le message ne doit pas dépasser 300 caractères")
    private String message;

    private Long createdAt;
}
