package com.parentapp.parent_assist.subscription;

import org.springframework.data.jpa.repository.JpaRepository;


public interface SubscriptionRepository extends JpaRepository<Subscription, String> {

    boolean existsByIdIgnoreCase(String id);

}
