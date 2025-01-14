package com.galimagroup.gestionproduit.repo;

import com.galimagroup.gestionproduit.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

}
