package com.parentapp.milestone;

import org.springframework.data.jpa.repository.JpaRepository;


public interface MilestoneRepository extends JpaRepository<Milestone, String> {
    boolean existsByIdIgnoreCase(String id);
}
