package com.parentapp.backend.auth.repository;

import com.parentapp.backend.auth.model.Role;
import com.parentapp.backend.auth.model.URole;
import com.parentapp.backend.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(URole name);
}
