package com.galimagroup.gestionproduit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {

    private String username;
    private String firstname;
    private String email;
    private String password;
}
