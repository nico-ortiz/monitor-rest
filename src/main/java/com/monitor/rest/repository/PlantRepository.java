package com.monitor.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monitor.rest.model.Plant;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    
    Optional<Plant> findByNameIgnoreCaseAndCountry(String name, String country);

    Optional<Plant> findByName(String name);
}
