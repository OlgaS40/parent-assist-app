package com.parentapp.parent_assist.materials_toys;

import org.springframework.data.jpa.repository.JpaRepository;


public interface MaterialsToysRepository extends JpaRepository<MaterialsToys, String> {

    boolean existsByIdIgnoreCase(String id);

}
