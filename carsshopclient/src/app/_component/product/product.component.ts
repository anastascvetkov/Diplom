import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { OrderPipe } from 'ngx-order-pipe';
import { throwIfEmpty } from 'rxjs';
import { Filters } from 'src/app/_model/filters';
import { UserService } from 'src/app/_services/user.service';
import { Product } from '../../_model/product';
import { ProductService } from '../../_services/product.service';
import { ProductDetailsComponent } from '../product-detail/product-details.component';

@Component({
  selector: 'product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

  public products: Product[] = [];
  public filters?: Filters;
  public brands?: string[] = [];
  public models?: string[] = [];
  
  constructor(private productService: ProductService, private router: Router,
    private productDetailsComponent: ProductDetailsComponent, private userService:UserService, 
    private orderPipe: OrderPipe) { }

  ngOnInit() {
    this.getAllAvailableProducts();
    this.getFilters();
  }

  onOrderBy(){
    const select = (<HTMLInputElement>document.getElementById('orderBy-select')).value;
    switch(select){
      case "BrandAsc": this.products = this.orderPipe.transform(this.products, 'car.brand', false); break;
      case "BrandDsc": this.products = this.orderPipe.transform(this.products, 'car.brand', true); break;
      case "PriceAsc": this.products = this.orderPipe.transform(this.products, 'price', false); break;
      case "PriceDsc": this.products = this.orderPipe.transform(this.products, 'price', true); break;
      case "YearOfCreationAsc": this.products = this.orderPipe.transform(this.products, 'car.engine.yearOfCreation', false); break;
      case "YearOfCreationDsc": this.products = this.orderPipe.transform(this.products, 'car.engine.yearOfCreation', true); break;

      default: this.orderPipe.transform(this.products, 'car.brand', false); break;
    }
  }

  public getAllAvailableProducts(): void {
    this.productService.getAllCarsForSale().subscribe(
      (response: Product[]) => {
        this.products = response;
      }
    );
    (error: HttpErrorResponse) => {
      alert(error.message);
    }
  }
  

  public navigateToPDP(id:any){
    this.router.navigate(['/productDetails', id]);
  }

  public getFilters(){
    this.productService.getFilters().subscribe(
      (response: Filters) => {
        this.filters = response;
        this.filters?.brand?.forEach(b => {
          this.brands?.push(b.brand || "");
        });
      }
    );
    (error: HttpErrorResponse) => {
      alert(error.message);
    }
  }

  onFilter(filterForm: NgForm): void{
    this.productService.getAllCarsForSaleFiltered(filterForm.value).subscribe(
      (response: Product[]) => {
        this.products = response;
      }
    );
    (error: HttpErrorResponse) => {
      alert(error.error);
    }
  }

  getModels(event : Event){
      const e =  event.target as HTMLInputElement;

      this.filters?.brand?.forEach(element => {
        if(element.brand === e.value){
          this.models = element.model;
        }
      });      
  }

}
