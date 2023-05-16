import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductDetailsComponent } from './_component/product-detail/product-details.component';
import { ProductComponent } from './_component/product/product.component';
import { UserComponent } from './_component/user/user.component';

const routes: Routes = [
  { path: 'myInfo', component: UserComponent },
  { path: '', component: ProductComponent },
  { path: 'productDetails/:id', component: ProductDetailsComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
