import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectionStrategy, Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router, RouteReuseStrategy } from '@angular/router';
import { from } from 'rxjs';
import { ChangePassword } from 'src/app/_model/chanegePassword';
import { Product } from 'src/app/_model/product';
import { ProductService } from 'src/app/_services/product.service';
import { User } from '../../_model/user';
import { UserService } from '../../_services/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  user: User = new User();


  constructor(private userService: UserService, private router: Router,
     private productService: ProductService) { }

  ngOnInit(): void {
    this.getUserInfo()
  }

  public getUserInfo(): void{
    this.userService.getUserInfo().subscribe(
      (response: User) => {
        this.user = response;

      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
    this.userService.getProductsForUser().subscribe(
      (response: Product[]) => {
        this.user.products = response;

      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  onOpenUserModal(modal: string): void{
    const button = document.createElement('button');
    const container = document.getElementById('main-container');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if(modal === "editUserData"){
      button.setAttribute('data-target', '#editUserInfoModal');
    }
    if(modal === "changePassword"){
      button.setAttribute('data-target', '#changePasswordModal');
    }
    container?.appendChild(button);
    button.click();
  }

  editUserInfo(form : NgForm):void{
    document.getElementById('close-change-password')?.click();
    this.userService.editUserInfo(form.value).subscribe(
      (response: User) => {
        this.router.navigate(['/myInfo']);
        this.user.contactName=response.contactName;
        this.user.email=response.email;
        this.user.address=response.address;
        this.user.phoneNumber=response.phoneNumber;
        form.reset();
        alert("User date changed successfull!");
      },
      (error: HttpErrorResponse) =>{
        alert(error.error);
        form.reset();
      }
    )
  }

  changePassword(form: NgForm):void{
    document.getElementById('close-edit-user-info')?.click();
    this.userService.changepassword(form.value).subscribe(
      (response: User) => {
        this.router.navigate(['/myInfo']);
        form.reset();
        alert("User password changed successfull!");
      },
      (error: HttpErrorResponse) =>{
        alert(error.error);
        form.reset();
      }
    )
  }

  navigateToPDP(id:any){
    this.router.navigate(['/productDetails', id]);
  }
  removeProduct(id:any){
    this.productService.removeProduct(id).subscribe(
      (response: User) => {
        this.router.navigate(['/myInfo']);
        alert("Product removed successfull!");
      },
      (error: HttpErrorResponse) =>{
        alert(error.error);
      }
    )
  }
}
