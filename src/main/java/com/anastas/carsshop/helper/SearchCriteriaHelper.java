package com.anastas.carsshop.helper;

import com.anastas.carsshop.model.enums.FuelType;
import com.anastas.carsshop.model.enums.TransmissionType;
import com.anastas.carsshop.model.enums.VehicleShape;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class SearchCriteriaHelper {

    @Positive(message = "Price must be positive!")
    private BigDecimal priceMin;

    @Positive(message = "Price must be positive!")
    private BigDecimal priceMax;

    private String brand;

    private String model;

    private VehicleShape shape;

    @Min(value = 1950)
    private Integer yearOfCreationMin;

    @Min(value = 1950)
    private Integer yearOfCreationMax;

    private FuelType fuel;

    private TransmissionType transmission;

    @Positive(message = "Power must be positive value!")
    private Integer powerMin;

    @Positive(message = "Power must be positive value!")
    private Integer powerMax;

    public SearchCriteriaHelper() {
    }

    public SearchCriteriaHelper(BigDecimal priceMin, BigDecimal priceMax, String brand, String model,
                                VehicleShape shape, Integer yearOfCreationMin, Integer yearOfCreationMax, FuelType fuel,
                                TransmissionType transmission, Integer powerMin, Integer powerMax) {
            this.priceMin = priceMin;
            this.priceMax = priceMax;
            this.brand = brand;
            this.model = model;
            this.shape = shape;
            this.yearOfCreationMin = yearOfCreationMin;
            this.yearOfCreationMax = yearOfCreationMax;
            this.fuel = fuel;
            this.transmission = transmission;
            this.powerMin = powerMin;
            this.powerMax = powerMax;
    }

    public BigDecimal getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(BigDecimal priceMin) {
        this.priceMin = priceMin;
    }

    public BigDecimal getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(BigDecimal priceMax) {
        this.priceMax = priceMax;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public VehicleShape getShape() {
        return shape;
    }

    public void setShape(VehicleShape shape) {
        this.shape = shape;
    }

    public Integer getYearOfCreationMin() {
        return yearOfCreationMin;
    }

    public void setYearOfCreationMin(Integer yearOfCreationMin) {
        this.yearOfCreationMin = yearOfCreationMin;
    }

    public Integer getYearOfCreationMax() {
        return yearOfCreationMax;
    }

    public void setYearOfCreationMax(Integer yearOfCreationMax) {
        this.yearOfCreationMax = yearOfCreationMax;
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

    public Integer getPowerMin() {
        return powerMin;
    }

    public void setPowerMin(Integer powerMin) {
        this.powerMin = powerMin;
    }

    public Integer getPowerMax() {
        return powerMax;
    }

    public void setPowerMax(Integer powerMax) {
        this.powerMax = powerMax;
    }
}
