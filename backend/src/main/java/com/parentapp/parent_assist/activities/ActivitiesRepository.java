package com.parentapp.parent_assist.activities;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ActivitiesRepository extends JpaRepository<Activities, String> {

    boolean existsByIdIgnoreCase(String id);

}
