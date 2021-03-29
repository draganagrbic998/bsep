import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user';
import { StorageService } from '../services/storage/storage.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(
    private storageService: StorageService
  ) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const user: User = this.storageService.getUser();
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
