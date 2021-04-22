import { Injectable } from '@angular/core';
import { User } from 'src/app/models/user';
import { SUPER_ADMIN } from 'src/app/utils/constants';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  readonly USER_KEY = 'auth';

  getUser(): User{
    return JSON.parse(localStorage.getItem(this.USER_KEY));
  }

  setUser(user: User): void{
    localStorage.setItem(this.USER_KEY, JSON.stringify(user));
    if (user.authorities.includes(SUPER_ADMIN)){
      (document.getElementById('receiver') as any).contentWindow
      .postMessage(JSON.stringify(user), 'https://localhost:4200');
    }
  }

  removeUser(): void{
    localStorage.removeItem(this.USER_KEY);
  }

}
