package com.parentapp.parent_assist.event_milestone;

import org.springframework.data.jpa.repository.JpaRepository;


public interface EventMilestoneRepository extends JpaRepository<EventMilestone, String> {

    boolean existsByIdIgnoreCase(String id);

}
