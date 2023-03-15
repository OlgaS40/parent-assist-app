package com.parentapp.material_toy;

import org.springframework.data.jpa.repository.JpaRepository;


public interface MaterialToyRepository extends JpaRepository<MaterialToy, String> {

    boolean existsByIdIgnoreCase(String id);

}
