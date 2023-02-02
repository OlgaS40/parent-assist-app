package com.parentapp.parent_assist.parents;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ParentsRepository extends JpaRepository<Parents, String> {

    boolean existsByIdIgnoreCase(String id);

}
