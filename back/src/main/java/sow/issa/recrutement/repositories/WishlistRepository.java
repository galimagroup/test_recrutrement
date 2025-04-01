package sow.issa.recrutement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sow.issa.recrutement.entities.WishlistEntity;

import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<WishlistEntity, Long> {
    Optional<WishlistEntity> findByUserId(Long userId);
}
