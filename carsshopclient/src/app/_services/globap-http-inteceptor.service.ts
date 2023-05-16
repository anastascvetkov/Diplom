import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {Observable, throwError} from "rxjs";
import {catchError} from 'rxjs/operators';
import { ErrorComponent } from '../_component/error/error.component';
import { ErrorStatus } from '../_model/error-status.enum';
import { AuthService } from './auth.service';

@Injectable({ providedIn: 'root' })
export class GlobalHttpInterceptorService implements HttpInterceptor {

  constructor(public router: Router, private modalService: NgbModal, private authService: AuthService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.authService.getAuthToken();

    if (token && req.url.includes('/') && !req.url.includes('/login')) {
      req = req.clone({
          setHeaders: {
            Authorization: `Bearer ${token}`,
          }
      });
    }

    return next.handle(req).pipe(
      catchError((error) => {

        if (error instanceof HttpErrorResponse) {
          if (error.error instanceof ErrorEvent) {
              console.error("Error Event");
          } else {
              console.log(`error status : ${error.status} ${error.statusText}`);
              switch (error.status) {
                  case 0:
                      this.openDialog(ErrorStatus.CONNECTION_REFUSED);
                      break;
                  case 401:
                      this.handle401(req);
                      break;
                  case 403:
                      this.router.navigateByUrl("/unauthorized");
                      break;
              }
          } 
        } else {
            console.error("Unknown error!");
        }

        return throwError(error.message);
      })
    )
  }

  private handle401(req: HttpRequest<any>) {
    if (req.url.includes('/login')) {
      this.openDialog(ErrorStatus.BAD_CREDENTIALS);
    } else {
      this.router.navigateByUrl("/login");
    }
  }

  private openDialog(status : ErrorStatus): void {
    const modalRef = this.modalService.open(ErrorComponent);
    modalRef.componentInstance.data = status;
  }
}
