package com.demba.back.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.demba.back.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);
}
