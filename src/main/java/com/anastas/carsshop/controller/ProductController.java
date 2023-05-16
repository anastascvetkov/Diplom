package com.anastas.carsshop.controller;

import com.anastas.carsshop.dto.FilterOptionsDTO;
import com.anastas.carsshop.excaption.BadRequestContentException;
import com.anastas.carsshop.excaption.ElementNotFoundException;
import com.anastas.carsshop.helper.ProductAllDataHelper;
import com.anastas.carsshop.helper.SearchCriteriaHelper;
import com.anastas.carsshop.model.Product;
import com.anastas.carsshop.model.User;
import com.anastas.carsshop.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/cars")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllCarsForSale(){
        return productService.getAllCarsForSale();
    }

    @GetMapping(path = "/user")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllCarsForUser(@RequestParam String username){
        return productService.getAllCarsForUser(username);
    }

    @GetMapping(path = "/owner")
    @ResponseStatus(HttpStatus.OK)
    public User getUserForProductCode(@RequestParam Long productCode){
        return productService.getUserForProductCode(productCode)
                .orElseThrow(() -> new ElementNotFoundException("No user found for product code = " + productCode));
    }



    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Product getProductByProductCode(@RequestParam Long productCode) {
        Optional<Product> product = productService.getProductByProductCode(productCode);
        if (product.isEmpty()) {
            throw new ElementNotFoundException("Product with product code = " + productCode + " not found!");
        }
        return product.get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody ProductAllDataHelper product, @RequestParam String username,
                                 Errors errors){
        if (errors.hasErrors()) {
            if (null != errors.getFieldError()) {
                throw new BadRequestContentException(errors.getFieldError().getDefaultMessage());
            } else {
                throw new BadRequestContentException();
            }
        }

        return productService.createProduct(product, username);
    }

    @PostMapping(path = "/images")
    @ResponseStatus(HttpStatus.CREATED)
    public Product addImage(@RequestParam("images") MultipartFile[] images, @RequestParam Long id){


        return productService.uploadImages(id, images);
    }

    @PostMapping(path = "/addImage")
    @ResponseStatus(HttpStatus.CREATED)
    public Product addImage(@RequestParam Long id, @RequestParam MultipartFile image){


        return productService.addImage(id, image);
    }

    @GetMapping(path = "/getimg", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody byte[] getImage(@RequestParam Long id){


        return productService.getImage(id);
    }
    @GetMapping(path = "/filters")
    @ResponseStatus(HttpStatus.OK)
    public FilterOptionsDTO getFilterOptions(){
        return productService.getFilterOptions();
    }

    @PostMapping(path = "/filtered")
    @ResponseStatus(HttpStatus.OK)
    private List<Product> filterCars(@RequestBody @Valid SearchCriteriaHelper searchCriteria, Errors errors){
        if (errors.hasErrors()) {
            if (null != errors.getFieldError()) {
                throw new BadRequestContentException(errors.getFieldError().getDefaultMessage());
            } else {
                throw new BadRequestContentException();
            }
        }
        return productService.filter(searchCriteria);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    private boolean removeProduct(@RequestParam Long productCode){
        return productService.removeProduct(productCode);
    }
}
