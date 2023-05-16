package com.anastas.carsshop.dto;

import com.anastas.carsshop.model.enums.FuelType;
import com.anastas.carsshop.model.enums.ProductStatus;
import com.anastas.carsshop.model.enums.TransmissionType;
import com.anastas.carsshop.model.enums.VehicleShape;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FilterOptionsDTO2 {
    private Set<ProductStatus> status;
    private Set<String> brand;
    private Set<String> model;
    private Set<VehicleShape> shape;
    private Set<FuelType> fuel;
    private Set<TransmissionType> transmission;
}
