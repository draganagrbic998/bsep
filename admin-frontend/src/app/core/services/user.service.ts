import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { User } from '../model/user';
import { Observable, of } from 'rxjs';
import { Authority } from '../model/authority';
import { Activation } from '../model/activation';
import { Page } from '../model/page';
import { catchError, map } from 'rxjs/operators';
import { Login } from '../model/login';
import { AuthToken } from '../model/auth-token';
import { EMPTY_PAGE } from '../utils/constants';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private readonly API_PATH = 'api/users';
  private readonly AUTH_PATH = 'auth';

  constructor(private httpClient: HttpClient) { }

  login(login: Login): Observable<AuthToken> {
    return this.httpClient.post<AuthToken>(`${this.AUTH_PATH}/login`, login).pipe(
      catchError(() => of(null))
    );
  }

  findAll(page: number, size: number): Observable<Page<User>> {
    const params = new HttpParams().set('page', String(page)).set('size', String(size));
    return this.httpClient.get<Page<User>>(this.API_PATH, {params}).pipe(
      catchError(() => of(EMPTY_PAGE))
    );
  }

  findAllAuthorities(): Observable<Authority[]> {
    return this.httpClient.get<Authority[]>(`${this.API_PATH}/authorities`).pipe(
      catchError(() => of([]))
    );
  }

  save(user: User): Observable<User> {
    if (user.id){
      return this.httpClient.put<User>(`${this.API_PATH}/${user.id}`, user).pipe(
        catchError(() => of(null))
      );
    }
    return this.httpClient.post<User>(this.API_PATH, user).pipe(
      catchError(() => of(null))
    );
  }

  delete(id: number): Observable<boolean> {
    return this.httpClient.delete<null>(`${this.API_PATH}/${id}`).pipe(
      map(() => true),
      catchError(() => of(false))
    );
  }

  sendActivationMail(id: number): Observable<boolean> {
    return this.httpClient.post<null>(`${this.API_PATH}/send/${id}`, null).pipe(
      map(() => true),
      catchError(() => of(false))
    );
  }

  getDisabled(uuid: string): Observable<User> {
    return this.httpClient.get<User>(`${this.AUTH_PATH}/disabled/${uuid}`).pipe(
      catchError(() => of(null))
    );
  }

  activate(activation: Activation): Observable<User> {
    return this.httpClient.post<User>(`${this.AUTH_PATH}/activate`, activation).pipe(
      catchError(() => of(null))
    );
  }

}
