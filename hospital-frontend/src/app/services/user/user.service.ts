import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Login } from 'src/app/models/login';
import { User } from 'src/app/models/user';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private http: HttpClient
  ) { }

  login(login: Login): Observable<User>{
    return this.http.post<User>(`${environment.authApi}/login`, login).pipe(
      catchError(() => of(null))
    );
  }

}
