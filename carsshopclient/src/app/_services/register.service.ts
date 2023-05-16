import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../_model/user';
import { UserRegister } from '../_model/user-register';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private http: HttpClient) { }

  public register(user: UserRegister): Observable<User>{
    return this.http.post<User>(`${environment.apiBaseUrl}/user/register`, user);
  }
}
