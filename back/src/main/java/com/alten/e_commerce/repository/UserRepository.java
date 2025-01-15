package com.alten.e_commerce.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.alten.e_commerce.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
  
  Optional<User> findByEmail(String email);

}
