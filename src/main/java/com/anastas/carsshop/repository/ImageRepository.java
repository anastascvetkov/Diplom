package com.anastas.carsshop.repository;

import com.anastas.carsshop.model.CarImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<CarImage, Long> {
}
