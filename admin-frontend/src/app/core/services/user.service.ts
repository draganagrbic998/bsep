import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { User } from '../model/user';
import { Observable, of } from 'rxjs';
import { Authority } from '../model/authority';
import { Activation } from '../model/activation';
import { Page } from '../model/page';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  users: User[] = [];
  authorities: Authority[];

  private readonly USERS_PATH = 'api/users';
  private readonly AUTH_PATH = 'auth';

  constructor(private httpClient: HttpClient) { }

  get(page: number, size: number): Observable<Page<User>> {
    const params = new HttpParams().set('page', String(page)).set('size', String(size));
    return this.httpClient.get<Page<User>>(this.USERS_PATH, {params}).pipe(
      catchError(() => of({content: [], totalElements: 0}))
    );
  }

  getAuthorities(): Observable<Authority[]> {
    return this.httpClient.get<Authority[]>(`${this.USERS_PATH}/authorities`).pipe(
      catchError(() => of([]))
    );
  }

  create(user: User): Observable<any> {
    return this.httpClient.post(this.USERS_PATH, user);
  }

  update(user: User): Observable<any> {
    return this.httpClient.put(this.USERS_PATH, user);
  }

  delete(user: User): Observable<null> {
    return this.httpClient.delete<null>(`${this.USERS_PATH}/${user.id}`);
  }

  getDisabled(uuid: string): Observable<User> {
    return this.httpClient.get<User>(`${this.AUTH_PATH}/disabled/${uuid}`);
  }

  activate(activation: Activation): Observable<User> {
    return this.httpClient.post<User>(`${this.AUTH_PATH}/activate`, activation);
  }

  sendActivationMail(id: number): Observable<null> {
    return this.httpClient.post<null>(`${this.USERS_PATH}/send/${id}`, null);
  }
}
