import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Login } from 'src/app/models/login';
import { User } from 'src/app/models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  private readonly AUTH_PATH = 'auth';

  login(login: Login): Observable<User>{
    return this.http.post<User>(`${this.AUTH_PATH}/login`, login);
  }

}
