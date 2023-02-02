package com.parentapp.parent_assist.plan;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PlanRepository extends JpaRepository<Plan, String> {

    boolean existsByIdIgnoreCase(String id);

}
