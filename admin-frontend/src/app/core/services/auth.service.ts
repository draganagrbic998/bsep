import { Injectable } from '@angular/core';

import { AuthToken } from '../model/auth-token';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly AUTH_KEY = 'auth-token';

  getToken(): AuthToken{
    return JSON.parse(localStorage.getItem(this.AUTH_KEY));
  }

  setToken(token: AuthToken): void{
    localStorage.setItem(this.AUTH_KEY, JSON.stringify(token));
  }

  removeToken(): void {
    localStorage.removeItem(this.AUTH_KEY);
  }

}
