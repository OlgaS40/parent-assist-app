package com.parentapp.parent_assist.relationships;

import org.springframework.data.jpa.repository.JpaRepository;


public interface RelationshipsRepository extends JpaRepository<Relationships, String> {

    boolean existsByIdIgnoreCase(String id);

}
