import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import {Admin} from '../model/admin';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {Login} from '../model/login';
import {log} from 'util';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  admin: BehaviorSubject<Admin | undefined>;
  token: BehaviorSubject<string | undefined>;

  constructor(
    private httpClient: HttpClient,
    private router: Router
  ) {
    this.admin = new BehaviorSubject<Admin | undefined>(JSON.parse(localStorage.getItem('admin') as string));
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

  login(login: Login): Observable<Admin> {
    return this.httpClient.post<Admin>('https://localhost:8080/auth/login', login).pipe(
      catchError(() => of(null))
    );
  }

  loggedIn(admin: Admin): void {
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

  getUser(): Admin{
    return this.get('admin') as Admin;
  }
}
