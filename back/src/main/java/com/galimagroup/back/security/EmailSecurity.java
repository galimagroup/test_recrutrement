package com.galimagroup.back.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("emailSecurity")
public class EmailSecurity {

    public boolean checkAdmin(Authentication authentication) {
        // Vérifie si l'authentification est non nulle et si l'email est celui de l'admin
        return authentication != null && "admin@admin.com".equals(authentication.getName());
    }
}
