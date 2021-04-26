import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { User } from '../model/user';
import { Observable, of } from 'rxjs';
import { Page } from '../model/page';
import { catchError, map } from 'rxjs/operators';
import { EMPTY_PAGE } from '../utils/constants';
import { Role } from '../model/role';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private readonly API_PATH = 'api/users';

  constructor(private httpClient: HttpClient) { }

  findAll(page: number, size: number): Observable<Page<User>> {
    const params = new HttpParams().set('page', String(page)).set('size', String(size));
    return this.httpClient.get<Page<User>>(this.API_PATH, {params}).pipe(
      catchError(() => of(EMPTY_PAGE))
    );
  }

  findAllRoles(): Observable<Role[]> {
    return this.httpClient.get<Role[]>(`${this.API_PATH}/roles`).pipe(
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

}
