package com.parentapp.backend.auth.repository;

import com.parentapp.backend.auth.model.Role;
import com.parentapp.backend.auth.model.URole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;


public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(URole name);
    Set<Role> findByReqNameIn(Set<String> reqNameList);
}
