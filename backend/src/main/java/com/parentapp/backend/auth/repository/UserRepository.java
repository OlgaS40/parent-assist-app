package com.parentapp.backend.auth.repository;

import com.parentapp.backend.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByIdIgnoreCase(String id);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}
