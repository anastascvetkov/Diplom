import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'orderBy'})

export class Product {
    id?: number;
    price?: number;
    status?: ProductStatus;
    car?: Car;
}

export class Car{
    id?: number;
    brand?: string;
    model?: string;
    description?: string;
    shape?: VehicleShape;
    engine?: Engine;
    images?: Image[];
}

export class Image{
    id?: number;
    image?: string;
}

export class Engine{
    id?: number;
    yearOfCreation?: string;
    fuel?: FuelType;
    transmission?: TransmissionType;
    power?: number;
}

enum TransmissionType{
    AUTOMATIC,
    MANUAL
}
enum FuelType{
    DIESEL,
    PETROL,
    ELECTRIC,
    LPG,
    MPG,
    HYBRID,
}
enum ProductStatus{
    AVAILABLE,
    SOLD
}
enum VehicleShape{
    SUV,
    CABRIOLET,
    WAGON,
    VAN,
    SPORT_CAR,
    SEDAN,
    HATCHBACK
}