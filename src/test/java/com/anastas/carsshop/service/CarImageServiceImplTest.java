package com.anastas.carsshop.service;

import com.anastas.carsshop.repository.CarRepository;
import com.anastas.carsshop.repository.ImageRepository;
import com.anastas.carsshop.excaption.ElementNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@Transactional
@SpringBootTest
public class CarImageServiceImplTest {

    @Autowired
    private CarImageServiceImpl carImageService;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private CarRepository carRepository;

    @Test
    public void givenCarIdThatNotExistWhenAddImageThenThrowElementNotFoundException(){
        given(carRepository.findById(1L)).willThrow(ElementNotFoundException.class);

        assertThrows(ElementNotFoundException.class, () -> carImageService.addImage(1L, "".getBytes()));
    }
}
