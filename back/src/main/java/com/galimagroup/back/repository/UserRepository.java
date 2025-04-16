package com.galimagroup.back.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.galimagroup.back.model.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
}
