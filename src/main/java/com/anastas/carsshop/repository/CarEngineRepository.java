package com.anastas.carsshop.repository;

import com.anastas.carsshop.model.CarEngine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarEngineRepository extends JpaRepository<CarEngine, Long> {
}
