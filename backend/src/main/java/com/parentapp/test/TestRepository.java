package com.parentapp.test;

import org.springframework.data.jpa.repository.JpaRepository;


public interface TestRepository extends JpaRepository<Test, String> {

    boolean existsByIdIgnoreCase(String id);

}
