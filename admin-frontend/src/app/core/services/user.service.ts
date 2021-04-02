import { Injectable } from '@angular/core';
import {CertificateInfo} from '../model/certificate-info';
import {HttpClient, HttpParams} from '@angular/common/http';
import {User} from '../model/user';
import {Observable} from 'rxjs';
import {Authority} from '../model/authority';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  users: User[] = [];
  authorities: Authority[];

  private readonly API_PATH = 'api/users';

  constructor(private httpClient: HttpClient) { }

  get(page: number, size: number): Observable<any> {
    const params = new HttpParams().set('page', String(page)).set('size', String(size));
    return this.httpClient.get(this.API_PATH, {params});
  }

  create(user: User): Observable<any> {
    return this.httpClient.post(this.API_PATH, user);
  }

  update(user: User): Observable<any> {
    return this.httpClient.put(this.API_PATH, user);
  }

  delete(user: User): Observable<any> {
    return this.httpClient.delete(`${this.API_PATH}/${user.id}`);
  }

  getAuthorities(): Observable<any> {
    return this.httpClient.get(`${this.API_PATH}/authorities`);
  }
}
