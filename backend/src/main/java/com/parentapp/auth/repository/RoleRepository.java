package com.parentapp.auth.repository;

import com.parentapp.auth.model.Role;
import com.parentapp.auth.model.URole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;


public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(URole name);
    Set<Role> findByReqNameIn(Set<String> reqNameList);
}
