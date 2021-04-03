import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {User} from '../model/user';
import {Observable} from 'rxjs';
import {Authority} from '../model/authority';
import {Activation} from '../model/activation';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  users: User[] = [];
  authorities: Authority[];

  private readonly USERS_PATH = 'api/users';
  private readonly AUTH_PATH = 'auth';

  constructor(private httpClient: HttpClient) { }

  get(page: number, size: number): Observable<any> {
    const params = new HttpParams().set('page', String(page)).set('size', String(size));
    return this.httpClient.get(this.USERS_PATH, {params});
  }

  create(user: User): Observable<any> {
    return this.httpClient.post(this.USERS_PATH, user);
  }

  update(user: User): Observable<any> {
    return this.httpClient.put(this.USERS_PATH, user);
  }

  delete(user: User): Observable<any> {
    return this.httpClient.delete(`${this.USERS_PATH}/${user.id}`);
  }

  getAuthorities(): Observable<any> {
    return this.httpClient.get(`${this.USERS_PATH}/authorities`);
  }

  getDisabled(uuid: string): Observable<any> {
    return this.httpClient.get(`${this.AUTH_PATH}/disabled/${uuid}`);
  }

  activate(activation: Activation): Observable<any> {
    return this.httpClient.post(`${this.AUTH_PATH}/activate`, activation);
  }

  sendActivationMail(id: number): Observable<any> {
    return this.httpClient.post(`${this.USERS_PATH}/send/${id}`, null);
  }
}
