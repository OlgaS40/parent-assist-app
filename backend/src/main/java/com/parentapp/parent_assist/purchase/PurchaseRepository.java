package com.parentapp.parent_assist.purchase;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PurchaseRepository extends JpaRepository<Purchase, String> {

    boolean existsByIdIgnoreCase(String id);

}
