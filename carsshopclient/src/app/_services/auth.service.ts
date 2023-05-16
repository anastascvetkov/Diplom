import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LoginResponse } from '../_model/login-response';
import { UserLogin } from '../_model/user-login';
import { AbstractService } from './abstract-service';

const URL_LOGIN = environment.apiBaseUrl + '/user/login';

@Injectable({
  providedIn: 'root'
})
export class AuthService extends AbstractService {

  constructor(private http: HttpClient) {
    super();
  }

  login(user:UserLogin) {
    return this.http.post<LoginResponse>(URL_LOGIN, user, this.httpOptions).pipe(
      tap(data => {
        localStorage.setItem('auth_token', data.jwtToken || "");
        localStorage.setItem('roles', data.authorities?.join(',') || "" );
        localStorage.setItem('username', data.username || "");
      })
    );
    
  }

  logout(): void{
    localStorage.removeItem('auth_token');
    localStorage.removeItem('roles');
    localStorage.removeItem('username');
  }

  getAuthToken(): string {
    const token = localStorage.getItem('auth_token');
    return token ? token : '';
  }

  hasRole(role: string): boolean {
    if(!this.isLoggedIn()) {
      return false;
    }

    let roles =  localStorage.getItem('roles')?.split(',');

    return roles?.includes(role) ? true : false;
  }

  isLoggedIn(): boolean {
   let authToken =  localStorage.getItem('auth_token');
   
   return authToken ? true : false;
  }

  getUsername(): string {
    let username = localStorage.getItem('username');

    return username ? username : '';
  }
}
