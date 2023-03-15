package com.parentapp.activity;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ActivityRepository extends JpaRepository<Activity, String> {

    boolean existsByIdIgnoreCase(String id);

}
