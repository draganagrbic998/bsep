import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

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

}
