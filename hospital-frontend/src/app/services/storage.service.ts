import { Injectable } from '@angular/core';
import { AuthToken } from 'src/app/models/auth-token';
import { USER_ROLE } from 'src/app/utils/constants';
import {Role} from '../models/role';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  readonly USER_KEY = 'auth';

  getToken(): AuthToken{
    return JSON.parse(localStorage.getItem(this.USER_KEY));
  }

  setToken(token: AuthToken): void{
    localStorage.setItem(this.USER_KEY, JSON.stringify(token));
    if (token.roles.some((r: Role) => r.name === USER_ROLE.SUPER_ADMIN)){
      (document.getElementById('receiver') as any).contentWindow
      .postMessage(JSON.stringify(token), 'https://localhost:4200');
    }
  }

  removeToken(): void{
    localStorage.removeItem(this.USER_KEY);
  }

}
