package com.parentapp.cashin;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CashInRepository extends JpaRepository<CashIn, String> {

    boolean existsByIdIgnoreCase(String id);

}
