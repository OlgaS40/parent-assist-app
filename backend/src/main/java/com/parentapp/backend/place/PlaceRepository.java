package com.parentapp.backend.place;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PlaceRepository extends JpaRepository<Place, String> {
}
