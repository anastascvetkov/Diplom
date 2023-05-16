package com.anastas.carsshop.service;

import com.anastas.carsshop.excaption.ElementNotFoundException;
import com.anastas.carsshop.model.Car;
import com.anastas.carsshop.model.Product;
import com.anastas.carsshop.model.enums.ProductStatus;
import com.anastas.carsshop.repository.CarEngineRepository;
import com.anastas.carsshop.repository.CarRepository;
import com.anastas.carsshop.repository.ProductRepository;
import com.anastas.carsshop.service.interfaces.CarImageService;
import com.anastas.carsshop.service.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@Transactional
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CarRepository carRepository;
    @Mock
    private CarEngineRepository carEngineRepository;
    @Mock
    private CarImageService carImageService;
    @Mock
    private UserService userService;

    @Test
    public void givenWhenGetAllCarsForSaleThenReturnListOfCarsWhichHaveStatusAvailable(){
        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, new BigDecimal(10), ProductStatus.AVAILABLE, new Car()));
        given(productRepository.findAll()).willReturn(products);

        assertArrayEquals(products.toArray(), productService.getAllCarsForSale().toArray());
    }

    @Test
    public void givenNullProductCodeWhenGetProductByProductCodeThenThrowElementNOtFound(){
        assertThrows(ElementNotFoundException.class, () -> productService.getProductByProductCode(null));
    }
    @Test
    public void givenProductCodeWhenGetProductByProductCodeThenOptionalOfProduct(){

        Product product = new Product(1L, new BigDecimal(10), ProductStatus.AVAILABLE, new Car());
        given(productService.getProductByProductCode(1L)).willReturn(Optional.of(product));
        assertEquals(product.getId(), productService.getProductByProductCode(1L).get().getId());
    }
}
