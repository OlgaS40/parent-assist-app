package com.parentapp.backend.plan_pricing;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PlanPricingRepository extends JpaRepository<PlanPricing, String> {

    boolean existsByIdIgnoreCase(String id);

}
