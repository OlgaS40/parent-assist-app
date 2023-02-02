package com.parentapp.parent_assist.questions;

import org.springframework.data.jpa.repository.JpaRepository;


public interface QuestionsRepository extends JpaRepository<Questions, String> {

    boolean existsByIdIgnoreCase(String id);

}
