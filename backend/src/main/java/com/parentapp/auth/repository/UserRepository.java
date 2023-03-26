package com.parentapp.auth.repository;

import com.parentapp.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByIdIgnoreCase(String id);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    User findByEmailIgnoreCase(String emailId);
    Boolean existsByEmail(String email);
}
