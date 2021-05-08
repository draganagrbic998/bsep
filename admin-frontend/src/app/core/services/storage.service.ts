import { Injectable } from '@angular/core';

import { AuthToken } from '../model/auth-token';
import { USER_ROLE } from '../utils/constants';
import {Role} from '../model/role';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  readonly TOKEN_KEY = 'auth';

  getToken(): AuthToken{
    return JSON.parse(localStorage.getItem(this.TOKEN_KEY));
  }

  setToken(token: AuthToken): void{
    localStorage.setItem(this.TOKEN_KEY, JSON.stringify(token));
    if (token.roles.some((r: Role) => r.name === USER_ROLE.ADMIN) ||
      token.roles.some((r: Role) => r.name === USER_ROLE.DOCTOR) ){
      (document.getElementById('receiver') as any).contentWindow
      .postMessage(JSON.stringify(token), 'https://localhost:4201');
    }
  }

  removeToken(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }

}
