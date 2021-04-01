import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { User } from '../model/user';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Login } from '../model/login';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  admin: BehaviorSubject<User | undefined>;
  token: BehaviorSubject<string | undefined>;

  constructor(
    private httpClient: HttpClient,
    private router: Router
  ) {
    this.admin = new BehaviorSubject<User | undefined>(JSON.parse(localStorage.getItem('admin') as string));
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

  getUser(): User{
    return this.get('admin') as User;
  }

  login(login: Login): Observable<User> {
    return this.httpClient.post<User>('auth/login', login).pipe(
      catchError(() => of(null))
    );
  }

  loggedIn(admin: User): void {
    this.admin.next(admin);
    this.set('admin', admin);
  }

  logout(): void {
    this.admin.next(null);
    this.remove('admin');
    this.router.navigateByUrl('/login');
  }

  isLoggedIn(): boolean {
    return !!this.admin.getValue();
  }

}
