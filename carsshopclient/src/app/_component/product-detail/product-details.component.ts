import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from 'src/app/_model/product';
import { User } from 'src/app/_model/user';
import { ProductService } from 'src/app/_services/product.service';
import { UserService } from 'src/app/_services/user.service';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {

  public product?: Product;
  public user?: User;
  productCode = 0;

  constructor(private userService:UserService, private route: ActivatedRoute, 
    private productService:ProductService) {
      this.productCode = this.route.snapshot.params['id'];
  }

  ngOnInit(): void {
    this.populateProduct();
  }

  public populateProduct():void{
    this.productService.getCarById(this.productCode).subscribe(
      (response: Product) => {
        this.product = response;
      }
    );
    (error: HttpErrorResponse) => {
      alert(error.message);
    }
    this.userService.getUserInfoForProduct(this.productCode).subscribe(
      (response: User) => {
        this.user = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );

  }
}
