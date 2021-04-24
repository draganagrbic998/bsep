import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Login } from 'src/app/models/login';
import { AuthToken } from 'src/app/models/auth-token';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  private readonly AUTH_PATH = 'auth';

  login(login: Login): Observable<AuthToken>{
    return this.http.post<AuthToken>(`${this.AUTH_PATH}/login`, login);
  }

}
