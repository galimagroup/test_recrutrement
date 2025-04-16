package com.galimagroup.back;

import com.galimagroup.back.model.Users;
import com.galimagroup.back.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Vérifier si l'utilisateur admin existe déjà
        if (!userRepository.findByEmail("admin@admin.com").isPresent()) {
            // Créer l'utilisateur admin
            Users admin = new Users();
            admin.setUsername("admin");
            admin.setFirstname("Admin");
            admin.setEmail("admin@admin.com");
            admin.setPassword(passwordEncoder.encode("adminPassword123"));
            userRepository.save(admin);
            System.out.println("Utilisateur admin ajouté !");
        }
    }
}
