import { Injectable } from '@angular/core';

import { AuthToken } from '../model/auth-token';
import { ADMIN, DOCTOR } from '../utils/constants';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  readonly TOKEN_KEY = 'auth';

  getToken(): AuthToken{
    return JSON.parse(localStorage.getItem(this.TOKEN_KEY));
  }

  setToken(token: AuthToken): void{
    localStorage.setItem(this.TOKEN_KEY, JSON.stringify(token));
    if (token.authorities.includes(ADMIN) || token.authorities.includes(DOCTOR)){
      (document.getElementById('receiver') as any).contentWindow
      .postMessage(JSON.stringify(token), 'https://localhost:4201');
    }
  }

  removeToken(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }

}
