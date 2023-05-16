import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AppComponent } from 'src/app/app.component';
import { UserLogin } from 'src/app/_model/user-login';
import { AuthService } from '../../_services/auth.service';

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }

  login(loginForm: NgForm): void{
    const button = document.getElementById('login-form-close');
    const username = (<HTMLInputElement>document.getElementById("username-login")).value;
    const password = (<HTMLInputElement>document.getElementById("password-login")).value;
    const user = new UserLogin(username, password)
    this.authService.login(user).subscribe(
      (response) => {
        AppComponent._isLoggedIn=true;
        button?.click();
        alert("Login successfully");
        loginForm.reset();
      },
      (error?: HttpErrorResponse) => {
        alert("Inccorect credentials!");
        loginForm.reset();
      }
    );
  }

  loginAfterRegister(user:UserLogin): void{
    this.authService.login(user).subscribe(
      (response) => {
        AppComponent._isLoggedIn=true;
      },
      (error?: HttpErrorResponse) => {
        alert("Inccorect credentials!");
      }
    );
  }
  
}
