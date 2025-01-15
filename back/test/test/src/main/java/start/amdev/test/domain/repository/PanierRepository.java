package start.amdev.test.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import start.amdev.test.domain.Model.Panier;

@Repository
public interface PanierRepository extends JpaRepository<Panier,Long> {
}
