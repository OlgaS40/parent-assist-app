package com.parentapp.question;

import org.springframework.data.jpa.repository.JpaRepository;


public interface QuestionRepository extends JpaRepository<Question, String> {

    boolean existsByIdIgnoreCase(String id);

}
