package com.anastas.carsshop.service.interfaces;

import com.anastas.carsshop.model.CarImage;

public interface CarImageService {

    CarImage addImage(Long id, byte[] image);
}
