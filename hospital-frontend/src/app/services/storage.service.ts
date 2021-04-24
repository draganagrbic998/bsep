import { Injectable } from '@angular/core';
import { AuthToken } from 'src/app/models/auth-token';
import { SUPER_ADMIN } from 'src/app/utils/constants';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  readonly USER_KEY = 'auth';

  getUser(): AuthToken{
    return JSON.parse(localStorage.getItem(this.USER_KEY));
  }

  setUser(token: AuthToken): void{
    localStorage.setItem(this.USER_KEY, JSON.stringify(token));
    if (token.authorities.includes(SUPER_ADMIN)){
      (document.getElementById('receiver') as any).contentWindow
      .postMessage(JSON.stringify(token), 'https://localhost:4200');
    }
  }

  removeUser(): void{
    localStorage.removeItem(this.USER_KEY);
  }

}
