package com.parentapp.parent_assist.schedule_activities;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleActivitiesRepository extends JpaRepository<ScheduleActivities, String> {

    boolean existsByIdIgnoreCase(String id);

}
