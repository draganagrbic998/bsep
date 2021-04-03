import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { AuthToken } from '../model/auth-token';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Login } from '../model/login';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  authToken: BehaviorSubject<AuthToken | undefined>;

  constructor(
    private httpClient: HttpClient,
    private router: Router
  ) {
    this.authToken = new BehaviorSubject<AuthToken | undefined>(JSON.parse(localStorage.getItem('auth-token') as string));
  }

  set(key: string, value: object): void{
    localStorage.setItem(key, JSON.stringify(value));
  }

  remove(key: string): void{
    localStorage.removeItem(key);
  }

  get(key: string): object{
    return JSON.parse(localStorage.getItem(key));
  }

  getUser(): AuthToken{
    return this.get('auth-token') as AuthToken;
  }

  login(login: Login): Observable<AuthToken> {
    return this.httpClient.post<AuthToken>('auth/login', login).pipe(
      catchError(() => of(null))
    );
  }

  loggedIn(admin: AuthToken): void {
    this.authToken.next(admin);
    this.set('auth-token', admin);
  }

  logout(): void {
    this.authToken.next(null);
    this.remove('auth-token');
    this.router.navigateByUrl('/login');
  }

  isLoggedIn(): boolean {
    return !!this.authToken.getValue();
  }

}
