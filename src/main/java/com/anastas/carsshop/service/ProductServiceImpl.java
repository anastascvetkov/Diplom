package com.anastas.carsshop.service;

import com.anastas.carsshop.dto.BrandDTO;
import com.anastas.carsshop.dto.FilterOptionsDTO;
import com.anastas.carsshop.dto.FilterOptionsDTO2;
import com.anastas.carsshop.excaption.ElementNotFoundException;
import com.anastas.carsshop.helper.ProductAllDataHelper;
import com.anastas.carsshop.helper.SearchCriteriaHelper;
import com.anastas.carsshop.model.*;
import com.anastas.carsshop.model.enums.FuelType;
import com.anastas.carsshop.model.enums.ProductStatus;
import com.anastas.carsshop.model.enums.TransmissionType;
import com.anastas.carsshop.model.enums.VehicleShape;
import com.anastas.carsshop.repository.CarEngineRepository;
import com.anastas.carsshop.repository.CarRepository;
import com.anastas.carsshop.repository.ProductRepository;
import com.anastas.carsshop.service.interfaces.CarImageService;
import com.anastas.carsshop.service.interfaces.ProductService;
import com.anastas.carsshop.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.LockModeType;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CarRepository carRepository;
    private final CarEngineRepository carEngineRepository;
    private final CarImageService carImageService;

    private final UserService userService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              CarRepository carRepository, CarEngineRepository carEngineRepository, CarImageService carImageService, UserService userService) {
        this.productRepository = productRepository;
        this.carRepository = carRepository;
        this.carEngineRepository = carEngineRepository;
        this.carImageService = carImageService;
        this.userService = userService;
    }

    @Override
    public List<Product> getAllCarsForSale() {
        return productRepository.findAll().stream()
                .filter(p -> p.getStatus().equals(ProductStatus.AVAILABLE))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Product> getProductByProductCode(Long productCode) {
        if (productCode == null) {
            throw new IllegalArgumentException("Product code cannot be null when search!");
        }
        return productRepository.findById(productCode);
    }

    @Override
    public Product createProduct(ProductAllDataHelper productHelper, String username) {
        if (null == productHelper) {
            throw new IllegalArgumentException("Product cannot be null on create!");
        }
        if (null == username || username.isBlank()) {
            throw new IllegalArgumentException("Invalid username to crate ne product!");
        }

        CarEngine carEngine = carEngineRepository.save(new CarEngine(productHelper.getYearOfCreation(),
                productHelper.getFuel(), productHelper.getTransmission(), productHelper.getPower()));

        Car car = carRepository.save(new Car(productHelper.getBrand(), productHelper.getModel(),
                productHelper.getDescription(), productHelper.getShape(), carEngine));

        Product product = new Product(productHelper.getPrice(), productHelper.getStatus(), car);

        Optional<User> currentUser =
                userService.findByUsername(username);

        if (currentUser.isEmpty()){
            throw new ElementNotFoundException("Cannot find current logged in user!");
        }else{
            product.setUser(currentUser.get());
        }
        product.setUser(currentUser.get());

        return productRepository.save(product);
    }

    @Lock(LockModeType.READ)
    @Override
    public List<Product> filter(SearchCriteriaHelper searchCriteria) {
        if (searchCriteria == null) {
            throw new IllegalArgumentException("Search criteria cannot be null!");
        }
        List<Product> products = productRepository.findAll();
        if (searchCriteria.getBrand() != null && !searchCriteria.getBrand().isBlank()) {
            products.removeIf(product -> !product.getCar().getBrand().trim().equalsIgnoreCase(searchCriteria.getBrand().trim()));
        }
        if (searchCriteria.getModel() != null && !searchCriteria.getModel().isBlank()) {
            products.removeIf(product -> !product.getCar().getModel().toLowerCase().trim().equalsIgnoreCase(searchCriteria.getModel().trim()));
        }
        if (searchCriteria.getFuel() != null) {
            products.removeIf(product -> !product.getCar().getEngine().getFuel().equals(searchCriteria.getFuel()));
        }
        if (searchCriteria.getShape() != null) {
            products.removeIf(product -> !product.getCar().getShape().equals(searchCriteria.getShape()));
        }
        if (searchCriteria.getTransmission() != null) {
            products.removeIf(product -> !product.getCar().getEngine().getTransmission()
                    .equals(searchCriteria.getTransmission()));
        }

        if (searchCriteria.getPriceMin() != null && searchCriteria.getPriceMax() != null) {
            if (searchCriteria.getPriceMin().compareTo(BigDecimal.ZERO) < 0 ||
                    searchCriteria.getPriceMax().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Min or Max price on filter cannot be negative!");
            }
            if (searchCriteria.getPriceMin().compareTo(searchCriteria.getPriceMax()) > 0) {
                throw new IllegalArgumentException("Minimal Price filter cannot be greater then maximal price!");
            }
            products.removeIf(product ->
                    searchCriteria.getPriceMin().compareTo(product.getPrice()) > 0 ||
                            searchCriteria.getPriceMax().compareTo(product.getPrice()) <= 0);
        } else {
            if (searchCriteria.getPriceMin() != null && searchCriteria.getPriceMin().compareTo(BigDecimal.ZERO) > 0 ) {
                products.removeIf(product ->
                        searchCriteria.getPriceMin().compareTo(product.getPrice()) > 0);
            }
            if (searchCriteria.getPriceMax() != null && searchCriteria.getPriceMax().compareTo(BigDecimal.ZERO) > 0) {
                products.removeIf(product ->
                        searchCriteria.getPriceMax().compareTo(product.getPrice()) < 0);
            }
        }
        if (searchCriteria.getPowerMin() != null && searchCriteria.getPowerMax() != null) {
            if (searchCriteria.getPowerMin() < 0 || searchCriteria.getPowerMax() < 0) {
                throw new IllegalArgumentException("Min or Max power on filter cannot be negative!");
            }
            if (searchCriteria.getPowerMin() > searchCriteria.getPowerMax()) {
                throw new IllegalArgumentException("Minimal Power filter cannot be greater then maximal power!");
            }
            products.removeIf(product ->
                    searchCriteria.getPowerMin() > product.getCar().getEngine().getPower() ||
                            searchCriteria.getPowerMax() < product.getCar().getEngine().getPower());
        } else {
            if (searchCriteria.getPowerMin() != null && searchCriteria.getPowerMin() >= 0) {
                products.removeIf(product ->
                        searchCriteria.getPowerMin() > product.getCar().getEngine().getPower());
            }
            if (searchCriteria.getPowerMax() != null && searchCriteria.getPowerMax() > 0) {
                products.removeIf(product ->
                        searchCriteria.getPowerMax() < product.getCar().getEngine().getPower());
            }
        }

        if (searchCriteria.getYearOfCreationMin() != null && searchCriteria.getYearOfCreationMax() != null) {
            if (searchCriteria.getYearOfCreationMin() > searchCriteria.getYearOfCreationMax()) {
                throw new IllegalArgumentException("Minimal year of creation filter cannot be after then maximal!");
            }
            products.removeIf(product ->
                    searchCriteria.getYearOfCreationMin() > product.getCar().getEngine().getYearOfCreation() ||
                            searchCriteria.getYearOfCreationMax() < product.getCar().getEngine().getYearOfCreation());
        } else {
            if (searchCriteria.getYearOfCreationMin() != null) {
                products.removeIf(product ->
                        searchCriteria.getYearOfCreationMin() > product.getCar().getEngine().getYearOfCreation());
            }
            if (searchCriteria.getYearOfCreationMax() != null) {
                products.removeIf(product ->
                        searchCriteria.getYearOfCreationMax() < product.getCar().getEngine().getYearOfCreation());
            }
        }

        return products;
    }

    @Override
    public List<Product> getAllCarsForUser(String username) {

        return productRepository.findAll().stream()
                .filter(p -> p.getUser().getUsername()
                        .equals(username))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> getUserForProductCode(Long productCode) {
        if (productCode == null || productCode < 0) {
            throw new IllegalArgumentException("Product code is not valid!");
        }
        Optional<Product> product = productRepository.findById(productCode);

        return product.map(Product::getUser);
    }

    @Override
    public FilterOptionsDTO getFilterOptions() {
        Set<ProductStatus> status = new HashSet<>();
        Set<BrandDTO> brand = new HashSet<>();
        Set<VehicleShape> shape = new HashSet<>();
        Set<FuelType> fuel = new HashSet<>();
        Set<TransmissionType> transmission = new HashSet<>();
        List<Product> allProducts = productRepository.findAll();
        allProducts.forEach(product -> {
            status.add(product.getStatus());

            if (brand.stream().anyMatch(brandDTO -> brandDTO.getBrand().trim().equalsIgnoreCase(product.getCar().getBrand().trim()))){
               brand.stream()
                       .filter(brandDTO -> brandDTO.getBrand().trim().equalsIgnoreCase(product.getCar().getBrand().trim()))
                       .findFirst()
                       .get().getModel().add(product.getCar().getModel().toUpperCase().trim());
            }else {
                brand.add(new BrandDTO(product.getCar().getBrand().toUpperCase().trim(),
                        new HashSet<>(){{add(product.getCar().getModel().toUpperCase().trim());}}));
            }


            shape.add(product.getCar().getShape());
            fuel.add(product.getCar().getEngine().getFuel());
            transmission.add(product.getCar().getEngine().getTransmission());

        });

        return new FilterOptionsDTO(status, brand, shape, fuel ,transmission);
    }

    @Override
    public FilterOptionsDTO2 getFilterOptions2() {
        Set<ProductStatus> status = new HashSet<>();
        Set<String> brand = new HashSet<>();
        Set<String> model = new HashSet<>();
        Set<VehicleShape> shape = new HashSet<>();
        Set<FuelType> fuel = new HashSet<>();
        Set<TransmissionType> transmission = new HashSet<>();
        List<Product> allProducts = productRepository.findAll();
        allProducts.forEach(product -> {
            status.add(product.getStatus());
            brand.add(product.getCar().getBrand());
            model.add(product.getCar().getBrand());
            shape.add(product.getCar().getShape());
            fuel.add(product.getCar().getEngine().getFuel());
            transmission.add(product.getCar().getEngine().getTransmission());

        });

        return new FilterOptionsDTO2(status, brand, model, shape, fuel ,transmission);
    }

    @Override
    public boolean removeProduct(Long productCode) {
        if (productCode == null) {
            throw new IllegalArgumentException("Product code cannot be null when search!");
        }
        Optional<Product> product = productRepository.findById(productCode);

        if (product.isEmpty()) {
            throw new IllegalArgumentException("No Product with product code = " + productCode + " found!");
        }

        try {
            carEngineRepository.delete(product.get().getCar().getEngine());
            carRepository.delete(product.get().getCar());
            productRepository.delete(product.get());
        }catch (IllegalArgumentException e){
            return false;
        }
        return true;
    }

    @Override
    public Product addImage(Long id, MultipartFile image) {
        if (id == null) {
            throw new IllegalArgumentException("Product code cannot be null when search!");
        }
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new IllegalArgumentException("No Product with product code = " + id + " found!");
        }

        try {
           product.get().getCar().getImages().add(carImageService.addImage(id, image.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return product.get();
    }

    @Override
    public byte[] getImage(Long id) {

        carRepository.findById(id);

        return carRepository.findById(id).get().getImages().iterator().next().getImage();
    }

    @Override
    public Product uploadImages(Long id, MultipartFile[] images) {
        if (id == null) {
            throw new IllegalArgumentException("Product code cannot be null when search!");
        }
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new IllegalArgumentException("No Product with product code = " + id + " found!");
        }

        try {
            for (MultipartFile f: images) {
                product.get().getCar().getImages().add(carImageService.addImage(id, f.getBytes()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return product.get();
    }
}
