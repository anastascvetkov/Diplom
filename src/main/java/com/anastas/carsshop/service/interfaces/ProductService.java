package com.anastas.carsshop.service.interfaces;

import com.anastas.carsshop.dto.FilterOptionsDTO;
import com.anastas.carsshop.dto.FilterOptionsDTO2;
import com.anastas.carsshop.helper.ProductAllDataHelper;
import com.anastas.carsshop.helper.SearchCriteriaHelper;
import com.anastas.carsshop.model.Product;
import com.anastas.carsshop.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getAllCarsForSale();

    Optional<Product> getProductByProductCode(Long productCode);

    Product createProduct(ProductAllDataHelper product, String username);

    List<Product> filter(SearchCriteriaHelper searchCriteria);

    List<Product> getAllCarsForUser(String username);

    Optional<User> getUserForProductCode(Long productCode);

    FilterOptionsDTO getFilterOptions();

    FilterOptionsDTO2 getFilterOptions2();

    boolean removeProduct(Long productCode);

    Product addImage(Long id, MultipartFile image);

    byte[] getImage(Long id);

    Product uploadImages(Long username, MultipartFile[] images);
}
