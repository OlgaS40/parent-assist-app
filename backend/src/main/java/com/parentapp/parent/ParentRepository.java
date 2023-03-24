package com.parentapp.parent;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ParentRepository extends JpaRepository<Parent, String> {

    boolean existsByIdIgnoreCase(String id);

}
