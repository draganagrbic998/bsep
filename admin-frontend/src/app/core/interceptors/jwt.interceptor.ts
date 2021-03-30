import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Admin } from '../model/admin';
import {AuthService} from '../services/auth.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  constructor(
    private authService: AuthService
  ) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const user: Admin = this.authService.getUser();
    if (!user){
      return next.handle(request);
    }

    request = request.clone({
      setHeaders: {
        Authorization: user.token
      }
    });
    return next.handle(request);
  }
}
