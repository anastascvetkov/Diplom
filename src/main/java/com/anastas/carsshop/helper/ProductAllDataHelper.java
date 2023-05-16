package com.anastas.carsshop.helper;

import com.anastas.carsshop.model.enums.FuelType;
import com.anastas.carsshop.model.enums.ProductStatus;
import com.anastas.carsshop.model.enums.TransmissionType;
import com.anastas.carsshop.model.enums.VehicleShape;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class ProductAllDataHelper {

    @NotNull(message = "Price is mandatory!")
    @Positive(message = "Price must be positive!")
    private BigDecimal price;

    @NotNull(message = "Status is mandatory!")
    private ProductStatus status;

    @NotBlank(message = "Brand is mandatory!")
    private String brand;

    @NotBlank(message = "Model is mandatory!")
    private String model;

    @NotBlank(message = "Description is mandatory!")
    private String description;

    private VehicleShape shape;

    @NotNull
    @Min(value = 1950, message = "Year of creation cannot be before 1950!")
    @Column(name = "year_of_creation", nullable = false)
    private Integer yearOfCreation;


    private FuelType fuel;

    @Column(name = "transmission")
    private TransmissionType transmission;

    @Positive(message = "Power must be positive value!")
    private Integer power;
//
//    @NotNull
//    private Set<CarImage> images;
}
