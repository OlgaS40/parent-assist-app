package com.parentapp.backend.child;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ChildRepository extends JpaRepository<Child, String> {

    boolean existsByIdIgnoreCase(String id);

}
