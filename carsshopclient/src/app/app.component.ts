import { HttpErrorResponse, HttpEventType, HttpResponse } from '@angular/common/http';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Product } from './_model/product';
import { ProductService } from './_services/product.service';
import { AuthService } from './_services/auth.service';
import { NavigationEnd, NavigationStart, Router } from '@angular/router';
import { UserComponent } from './_component/user/user.component';
import { Observable, Subscription } from 'rxjs';
import { FileUploadService } from './_services/image-upload.service';
import { ProductComponent } from './_component/product/product.component';
import { ConstantPool } from '@angular/compiler';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit{

  browserRefresh = false;
  selectedFiles?: FileList;
  progressInfos: any[] = [];
  message: string[] = [];
  previews: string[] = [];
  imageInfos?: Observable<any>;
  myFiles: FormData = new FormData();

  public static _isLoggedIn = false;
  subscription: Subscription;

  public get isLoggedIn() {
    return AppComponent._isLoggedIn;
  }
  public set isLoggedIn(value) {
    AppComponent._isLoggedIn = value;
  }

  constructor(private productService: ProductService, private authService:AuthService,
    private userComponent: UserComponent, private router: Router, private productComponent:ProductComponent) {

      this.subscription = router.events.subscribe((event) => {
        if (event instanceof NavigationStart) {
          this.browserRefresh = !router.navigated;
          this.isLoggedIn = authService.isLoggedIn();
        }
      });
     }

    public isAuthenticated: boolean = false;

    
  ngOnInit(): void {
    const session = localStorage.getItem("SessionId");
    if(session !== null){
        this.isAuthenticated = true;
    }
  }

  selectFiles(event: any): void {
    this.message = [];
    this.progressInfos = [];
    this.selectedFiles = event.target.files;
    this.previews = [];
    if (this.selectedFiles && this.selectedFiles[0]) {
      const numberOfFiles = this.selectedFiles.length;
      for (let i = 0; i < numberOfFiles; i++) {
        const reader = new FileReader();
        reader.onload = (e: any) => {
          this.previews.push(e.target.result);
        };
        reader.readAsDataURL(this.selectedFiles[i]);
      }
    }
  }
  

  public onOpenModal(mode: string): void{
    const button = document.createElement('button');
    const container = document.getElementById('main-container');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'addProduct'){
      button.setAttribute('data-target', '#addProductModal');
    }
    if (mode === 'showMyProducts'){
      button.setAttribute('data-target', '#myProductsModal');
    }
    if (mode === 'showLogin'){
      button.setAttribute('data-target', '#loginModal');
    } 
    if (mode === 'register'){
      button.setAttribute('data-target', '#registerModal');
    } 
    if (mode === 'filterCars'){
      this.router.navigate(['/']);
    }
    

    container?.appendChild(button);
    button.click();
  }
  uploadIimages(event : any){
    for (var i = 0; i < event.target.files.length; i++) { 
      this.myFiles.append("images",  event.target.files[i])
  };
  }

  public onAddProduct(addForm: NgForm): void{
    document.getElementById('add-product-form-close')?.click();
    this.productService.addNewProduct(addForm.value).subscribe(
      (response: Product) => {
        this.productService.uploadImage(this.myFiles, response.id || 0).subscribe();
        addForm.reset();
        this.productComponent.getAllAvailableProducts();
        this.router.navigate(["/"]);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
      }
    );
    
  }

  logout(){
    this.authService.logout();
    AppComponent._isLoggedIn=false;
    this.router.navigate(['/']);
  }

  title = 'carsshopclient';
}
