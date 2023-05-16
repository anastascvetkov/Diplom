package com.anastas.carsshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.anastas.carsshop.model.enums.ProductStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Price is mandatory!")
    @Positive(message = "Price must be positive!")
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @NotNull(message = "Status is mandatory!")
    @Column(name = "status", nullable = false)
    private ProductStatus status;

    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Product(Long id,BigDecimal price, ProductStatus status, Car car, User user) {
        this.id = id;
        this.price = price;
        this.status = status;
        this.car = car;
        this.user = user;
    }

    public Product() {
    }

    public Product(Long id, BigDecimal price, ProductStatus status, Car car) {
        this.id = id;
        this.price = price;
        this.status = status;
        this.car = car;
    }

    public Product(BigDecimal price, ProductStatus status, Car car) {
        this.price = price;
        this.status = status;
        this.car = car;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
