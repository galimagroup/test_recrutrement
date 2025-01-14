package com.galimagroup.gestionproduit.repo;

import com.galimagroup.gestionproduit.entity.Produit;
import com.galimagroup.gestionproduit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);


}
