package start.amdev.test.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import start.amdev.test.domain.Model.Produit;

import java.util.Optional;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    Optional<Object> findProduitByCode(String code);
}
