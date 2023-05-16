package com.anastas.carsshop.model;

import com.anastas.carsshop.model.enums.VehicleShape;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Brand is mandatory!")
    @Column(name = "brand", nullable = false)
    private String brand;

    @NotBlank(message = "Model is mandatory!")
    @Column(name = "model", nullable = false)
    private String model;

    @NotBlank(message = "Description is mandatory!")
    @Column(name = "description")
    private String description;
    @Column(name = "shape", nullable = false)
    private VehicleShape shape;

    @OneToOne
    @JoinColumn(name = "engine_id")
    private CarEngine engine;

    @OneToMany(targetEntity = CarImage.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "car")
    private Set<CarImage> images;

    public Car() {
    }

    public Car(Long id, String brand, String model, String description, VehicleShape shape, CarEngine engine, Set<CarImage> images) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.description = description;
        this.shape = shape;
        this.engine = engine;
        this.images = images;
    }

    public Car(Long id, String brand, String model, String description, CarEngine engine, VehicleShape shape) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.description = description;
        this.engine = engine;
        this.shape = shape;
    }

    public Car(String brand, String model, String description, VehicleShape shape, CarEngine engine) {
        this.brand = brand;
        this.model = model;
        this.description = description;
        this.shape = shape;
        this.engine = engine;
    }

    public Set<CarImage> getImages() {
        return images;
    }

    public void setImages(Set<CarImage> images) {
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CarEngine getEngine() {
        return engine;
    }

    public void setEngine(CarEngine engine) {
        this.engine = engine;
    }

    public VehicleShape getShape() {
        return shape;
    }

    public void setShape(VehicleShape shape) {
        this.shape = shape;
    }
}
