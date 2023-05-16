import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ProductComponent } from './product.component';


@NgModule({
  declarations: [

  ],
  imports: [
    BrowserModule,
    HttpClientModule,
  ],
  providers: [
    ProductComponent
  ]
})
export class ProductModule { }
