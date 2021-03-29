import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
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

  constructor(private httpClient: HttpClient,
              private router: Router) {
    this.admin = new BehaviorSubject<Admin | undefined>(JSON.parse(localStorage.getItem('admin') as string));
    this.token = new BehaviorSubject<string | undefined>(localStorage.getItem('token') as string);
  }

  login(login: Login): Observable<any> {
    return this.httpClient.post('/auth/login', login);
  }

  loggedIn(token: string, admin: Admin): void {
    this.admin.next(admin);
    this.token.next(token);
    localStorage.setItem('admin', JSON.stringify(admin));
    localStorage.setItem('token', token);
  }

  logout(): void {
    this.admin.next(null);
    this.token.next(null);
    localStorage.removeItem('admin');
    localStorage.removeItem('token');
    this.router.navigateByUrl('/login');
  }

  isLoggedIn(): boolean {
    return !!this.admin.getValue();
  }
}
