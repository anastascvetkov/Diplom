package com.anastas.carsshop.service;

import com.anastas.carsshop.excaption.ElementNotFoundException;
import com.anastas.carsshop.model.Car;
import com.anastas.carsshop.model.CarImage;
import com.anastas.carsshop.repository.CarRepository;
import com.anastas.carsshop.repository.ImageRepository;
import com.anastas.carsshop.service.interfaces.CarImageService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarImageServiceImpl implements CarImageService {

    private final ImageRepository imageRepository;
    private final CarRepository carRepository;

    public CarImageServiceImpl(ImageRepository imageRepository, CarRepository carRepository) {
        this.imageRepository = imageRepository;
        this.carRepository = carRepository;
    }

    @Override
    public CarImage addImage(Long id, byte[] image) {
        CarImage carImage = new CarImage();

        Optional<Car> car = carRepository.findById(id);

        if (car.isEmpty()){
            throw  new ElementNotFoundException(" Car with id = " + id + " not found!");
        }
        carImage.setImage(image);
        carImage.setCar(car.get());

        return imageRepository.save(carImage);
    }
}
