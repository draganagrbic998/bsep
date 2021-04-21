import { Injectable } from '@angular/core';
import { User } from 'src/app/models/user';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  private readonly USER_KEY = 'auth';

  constructor(){
    window.addEventListener('message', e => {
      if (e.origin === 'https://localhost:4200') {
        localStorage.setItem(this.USER_KEY, e.data);
      }
    });
  }

  getUser(): User{
    return JSON.parse(localStorage.getItem(this.USER_KEY));
  }

  setUser(user: User): void{
    const message = JSON.stringify(user);
    const receiver = (document.getElementById('receiver') as any).contentWindow;
    receiver.postMessage(message, 'https://localhost:4200');
    localStorage.setItem(this.USER_KEY, message);
  }

  removeUser(): void{
    localStorage.removeItem(this.USER_KEY);
  }

}
