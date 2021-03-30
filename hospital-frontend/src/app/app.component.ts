import { Component } from '@angular/core';
import { StorageService } from './services/storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  constructor(
    private storageService: StorageService
  ){}

  get authority(): string{
    return this.storageService.getUser()?.authorities[0];
  }

}
