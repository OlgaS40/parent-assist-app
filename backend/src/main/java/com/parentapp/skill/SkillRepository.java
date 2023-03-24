package com.parentapp.skill;

import org.springframework.data.jpa.repository.JpaRepository;


public interface SkillRepository extends JpaRepository<Skill, String> {

    boolean existsByIdIgnoreCase(String id);

}
