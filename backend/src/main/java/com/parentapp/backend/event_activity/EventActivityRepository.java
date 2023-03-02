package com.parentapp.backend.event_activity;

import org.springframework.data.jpa.repository.JpaRepository;


public interface EventActivityRepository extends JpaRepository<EventActivity, String> {

    boolean existsByIdIgnoreCase(String id);

}
