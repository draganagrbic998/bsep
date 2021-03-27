import { Component } from '@angular/core';
import { StorageService } from './services/storage/storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  constructor(
    private storageService: StorageService
  ){}

  get role(): string{
    return 'ADMIN';
    return 'DOCTOR';
    return this.storageService.getUser()?.role;
  }

}
