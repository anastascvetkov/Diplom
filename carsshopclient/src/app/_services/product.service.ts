import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { Product } from '../_model/product';
import { environment } from 'src/environments/environment';
import { Filters } from '../_model/filters';
import { SearchCriteria } from '../_model/searchCriteria';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  
  

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http:HttpClient) { }

  public getAllCarsForSale(): Observable<Product[]>{
    return this.http.get<Product[]>(`${this.apiServerUrl}/cars/all`);
  }

  public addNewProduct(product: Product): Observable<Product>{
    return this.http.post<Product>(`${this.apiServerUrl}/cars`, product, 
    {params: {'username': localStorage.getItem('username') || ""}});
  }

  public uploadImage(formData : FormData , id:number){
    return this.http.post(`${this.apiServerUrl}/cars/images`, formData,
    {params: {id}});
  }

  public getCarById(productCode: number): Observable<Product>{
    return this.http.get<Product>(`${this.apiServerUrl}/cars`, 
    {params: {'productCode': productCode}})
  }

  public getFilters(): Observable<Filters> {
    return this.http.get<Filters>(`${this.apiServerUrl}/cars/filters`)
  }

  public getAllCarsForSaleFiltered(searchCriteria: SearchCriteria): Observable<Product[]>{
    if(searchCriteria.brand === ""){
      searchCriteria.brand = undefined;
    }
    if(searchCriteria.model === ""){
      searchCriteria.model = undefined;
    }
    if(searchCriteria.fuel === ""){
      searchCriteria.fuel = undefined;
    }
    if(searchCriteria.transmission === ""){
      searchCriteria.transmission = undefined;
    }
    if(searchCriteria.shape === ""){
      searchCriteria.shape = undefined;
    }

    return this.http.post<Product[]>(`${this.apiServerUrl}/cars/filtered`, searchCriteria);
  }

  public removeProduct(id: number): Observable<any>{
    return this.http.delete(`${this.apiServerUrl}/cars`, 
    {params: {'productCode': id}})
  }
}
