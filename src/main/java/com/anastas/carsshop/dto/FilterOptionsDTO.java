package com.anastas.carsshop.dto;

import com.anastas.carsshop.model.enums.FuelType;
import com.anastas.carsshop.model.enums.ProductStatus;
import com.anastas.carsshop.model.enums.TransmissionType;
import com.anastas.carsshop.model.enums.VehicleShape;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class FilterOptionsDTO {
    private Set<ProductStatus> status;
    private Set<BrandDTO> brand;
    private Set<VehicleShape> shape;
    private Set<FuelType> fuel;
    private Set<TransmissionType> transmission;

    public FilterOptionsDTO(Set<ProductStatus> status, Set<BrandDTO> brand, Set<VehicleShape> shape,
                            Set<FuelType> fuel, Set<TransmissionType> transmission) {
        this.status = status;
        this.brand = brand;
        this.shape = shape;
        this.fuel = fuel;
        this.transmission = transmission;
    }
}
