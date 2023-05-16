import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProductComponent } from './_component/product/product.component';
import { ProductService } from './_services/product.service';
import { LoginComponent } from './_component/login/login.component';
import { RegisterComponent } from './_component/register/register.component';
import { CookieService } from 'ngx-cookie-service';
import { UserComponent } from './_component/user/user.component';
import { ProductDetailsComponent } from './_component/product-detail/product-details.component';
import { OrderPipe } from 'ngx-order-pipe';


@NgModule({
  declarations: [
    AppComponent,
    ProductComponent,
    LoginComponent,
    UserComponent,
    RegisterComponent,
    ProductDetailsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
  ],
  
  providers: [ProductService, ProductComponent, UserComponent, CookieService, LoginComponent, ProductDetailsComponent, OrderPipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
