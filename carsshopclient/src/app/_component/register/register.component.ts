import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/_model/user';
import { UserLogin } from 'src/app/_model/user-login';
import { UserRegister } from 'src/app/_model/user-register';
import { UserRegisterPassConfirm } from 'src/app/_model/user-register-pass-confirm';
import { ProductService } from 'src/app/_services/product.service';
import { RegisterService } from 'src/app/_services/register.service';
import { LoginComponent } from '../login/login.component';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private registerService: RegisterService, private router: Router,
     private loginComponent: LoginComponent) { }

  ngOnInit(): void {
  }

  public onRegisterUser(registerForm: NgForm) {
    document.getElementById('register-form-close')?.click();
    const username = (<HTMLInputElement>document.getElementById("username")).value;
    const password = (<HTMLInputElement>document.getElementById("password")).value;
    const confirmPassword = (<HTMLInputElement>document.getElementById("passwordConfirm")).value

    if (this.validateRegisterInputPassword(password, confirmPassword, registerForm)) {
      document.getElementById('register-form-close')?.click();
      const user = new UserRegister(username, password);
      this.registerService.register(user).subscribe(
        (response: User) => {
          registerForm.reset();
          this.loginComponent.loginAfterRegister(new UserLogin(username, password));
        },
        (error: HttpErrorResponse) => {
          alert(error.error);
          registerForm.reset();
        }
      );
      this.router.navigate(['/']);
    } else {
      const button = document.createElement('button');
      const container = document.getElementById('main-container');
      button.type = 'button';
      button.style.display = 'none';
      button.setAttribute('data-toggle', 'modal');
      button.setAttribute('data-target', '#registerModal');
      container?.appendChild(button);
      button.click();
    }
  }

  private validateRegisterInputPassword(pasword: string, passConfirm: string, registerForm: NgForm): boolean {
    if (pasword !== passConfirm) {
      alert("Passwords do not match!");
      registerForm.reset();
      return false;
    } else {
      return true;
    }
  }

}
