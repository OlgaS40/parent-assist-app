package com.parentapp.backend.schedule_activity;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleActivityRepository extends JpaRepository<ScheduleActivity, String> {

    boolean existsByIdIgnoreCase(String id);

}
