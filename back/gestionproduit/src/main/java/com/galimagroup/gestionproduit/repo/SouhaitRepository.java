package com.galimagroup.gestionproduit.repo;

import com.galimagroup.gestionproduit.entity.Panier;
import com.galimagroup.gestionproduit.entity.Souhait;
import com.galimagroup.gestionproduit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SouhaitRepository extends JpaRepository<Souhait, Long> {
    Optional<Souhait> findByUserId(Long userId);

}
