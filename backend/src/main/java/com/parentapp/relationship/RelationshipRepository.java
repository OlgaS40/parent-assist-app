package com.parentapp.relationship;

import org.springframework.data.jpa.repository.JpaRepository;


public interface RelationshipRepository extends JpaRepository<Relationship, String> {

    boolean existsByIdIgnoreCase(String id);

}
