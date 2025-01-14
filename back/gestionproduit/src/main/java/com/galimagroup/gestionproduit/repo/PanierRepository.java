package com.galimagroup.gestionproduit.repo;

import com.galimagroup.gestionproduit.entity.Panier;
import com.galimagroup.gestionproduit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PanierRepository extends JpaRepository<Panier, Long> {
    Optional<Panier> findByUserId(Long userId);

}
