package com.anastas.carsshop.model;

import com.anastas.carsshop.model.enums.FuelType;
import com.anastas.carsshop.model.enums.TransmissionType;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "car_engine")
public class CarEngine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 1950, message = "Year of creation cannot be before 1950!")
    @Column(name = "year_of_creation", nullable = false)
    private Integer yearOfCreation;

    @Column(name = "fuel", nullable = false)
    private FuelType fuel;

    @Column(name = "transmission")
    private TransmissionType transmission;

    @Positive(message = "Power must be positive value!")
    @Column(name = "power", nullable = false)
    private Integer power;

    public CarEngine() {
    }

    public CarEngine(Long id, Integer yearOfCreation, FuelType fuel, TransmissionType transmission, Integer power) {
        this.id = id;
        this.yearOfCreation = yearOfCreation;
        this.fuel = fuel;
        this.transmission = transmission;
        this.power = power;
    }

    public CarEngine(Integer yearOfCreation, FuelType fuel, TransmissionType transmission, Integer power) {
        this.yearOfCreation = yearOfCreation;
        this.fuel = fuel;
        this.transmission = transmission;
        this.power = power;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYearOfCreation() {
        return yearOfCreation;
    }

    public void setYearOfCreation(Integer yearOfCreation) {
        this.yearOfCreation = yearOfCreation;
    }

    public FuelType getFuel() {
        return fuel;
    }

    public void setFuel(FuelType fuel) {
        this.fuel = fuel;
    }

    public TransmissionType getTransmission() {
        return transmission;
    }

    public void setTransmission(TransmissionType transmission) {
        this.transmission = transmission;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }
}
