import { Injectable } from '@angular/core';

import { AuthToken } from '../model/auth-token';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly TOKEN_KEY = 'auth';

  constructor(){
    window.addEventListener('message', e => {
      if (e.origin === 'https://localhost:4201') {
        localStorage.setItem(this.TOKEN_KEY, e.data);
      }
    });
  }

  getToken(): AuthToken{
    return JSON.parse(localStorage.getItem(this.TOKEN_KEY));
  }

  setToken(token: AuthToken): void{
    const message = JSON.stringify(token);
    const receiver = (document.getElementById('receiver') as any).contentWindow;
    receiver.postMessage(message, 'https://localhost:4201');
    localStorage.setItem(this.TOKEN_KEY, message);
  }

  removeToken(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }

}
