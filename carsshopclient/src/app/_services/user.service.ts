import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ChangePassword } from '../_model/chanegePassword';
import { Product } from '../_model/product';
import { User } from '../_model/user';
import { UserData } from '../_model/user-data';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiServerUrl = environment.apiBaseUrl;

  changePasswordToSend?: ChangePassword;

  constructor(private http:HttpClient, private cookieService: CookieService) { }

  public getUserInfo(): Observable<User>{
    return this.http.get(`${this.apiServerUrl}/user/info`,{
      params: {'username': localStorage.getItem('username') || ""}
    });
  }

  public getProductsForUser(): Observable<Product[]>{
    return this.http.get<Product[]>(`${this.apiServerUrl}/cars/user`, 
    {params: {'username': localStorage.getItem('username') || ""}
    });

  }

  public editUserInfo(userData: UserData): Observable<User>{
    return this.http.patch(`${this.apiServerUrl}/user/data`, userData,
    {params: {'username': localStorage.getItem('username') || ""}});
  }

  public changepassword(changePassHelper: ChangePassword): Observable<User>{
    this.changePasswordToSend = changePassHelper;
    this.changePasswordToSend.username = localStorage.getItem('username') || "";
    return this.http.patch(`${this.apiServerUrl}/user/password`, changePassHelper)
  }

  public getUserInfoForProduct(productCode:number): Observable<User>{
    return this.http.get<User>(`${this.apiServerUrl}/cars/owner`,
    {params: {'productCode': productCode}});
  }
}
