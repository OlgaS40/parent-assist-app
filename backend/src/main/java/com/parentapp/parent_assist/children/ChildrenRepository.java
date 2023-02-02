package com.parentapp.parent_assist.children;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ChildrenRepository extends JpaRepository<Children, String> {

    boolean existsByIdIgnoreCase(String id);

}
