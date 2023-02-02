package com.parentapp.parent_assist.skills;

import org.springframework.data.jpa.repository.JpaRepository;


public interface SkillsRepository extends JpaRepository<Skills, String> {

    boolean existsByIdIgnoreCase(String id);

}
