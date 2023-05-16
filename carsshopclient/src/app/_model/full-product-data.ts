export class FullProduct {


} export interface Product {
    id?: number;
    price?: number;
    status?: ProductStatus;
    car?: Car;
}

export interface Car {
    id?: number;
    brand?: string;
    model?: string;
    description?: string;
    shape?: VehicleShape;
    engine?: Engine;
    
}

export interface Engine {
    id?: number;
    yearOfCreation?: string;
    fuel?: FuelType;
    transmission?: TransmissionType;
    power?: number;
}

enum TransmissionType {
    AUTOMATIC,
    MANUAL
}
enum FuelType {
    DIESEL,
    PETROL,
    ELECTRIC,
    LPG,
    MPG,
    HYBRID,
}
enum ProductStatus {
    AVAILABLE,
    SOLD
}
enum VehicleShape {
    SUV,
    CABRIOLET,
    WAGON,
    VAN,
    SPORT_CAR,
    SEDAN,
    HATCHBACK
}