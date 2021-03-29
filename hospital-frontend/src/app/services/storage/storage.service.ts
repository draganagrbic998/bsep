import { Injectable } from '@angular/core';
import { User } from 'src/app/models/user';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  readonly USER_KEY = 'user';
  readonly PATIENT_KEY = 'patient';

  set(key: string, value: object): void{
    localStorage.setItem(key, JSON.stringify(value));
  }

  remove(key: string): void{
    localStorage.removeItem(key);
  }

  get(key: string): object{
    return JSON.parse(localStorage.getItem(key));
  }

  setUser(user: User): void{
    this.set(this.USER_KEY, user);
  }

  removeUser(): void{
    this.remove(this.USER_KEY);
  }

  getUser(): User{
    return this.get(this.USER_KEY) as User;
  }

}
