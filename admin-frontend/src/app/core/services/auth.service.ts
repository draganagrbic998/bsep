import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Activation } from '../model/activation';
import { AuthToken } from '../model/auth-token';
import { Login } from '../model/login';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient) { }

  private readonly AUTH_PATH = 'auth';

  login(login: Login): Observable<AuthToken> {
    return this.httpClient.post<AuthToken>(`${this.AUTH_PATH}/login`, login);
  }

  activate(activation: Activation): Observable<User> {
    return this.httpClient.post<User>(`${this.AUTH_PATH}/activate`, activation).pipe(
      catchError(() => of(null))
    );
  }

  getDisabled(uuid: string): Observable<User> {
    return this.httpClient.get<User>(`${this.AUTH_PATH}/disabled/${uuid}`).pipe(
      catchError(() => of(null))
    );
  }

}
