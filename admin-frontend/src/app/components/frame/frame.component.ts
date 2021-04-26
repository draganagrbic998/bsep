import { Component } from '@angular/core';
import { StorageService } from 'src/app/core/services/storage.service';

@Component({
  selector: 'app-frame',
  templateUrl: './frame.component.html',
  styleUrls: ['./frame.component.scss']
})
export class FrameComponent {

  constructor(private storageService: StorageService) {
    window.addEventListener('message', e => {
      if (e.origin === 'https://localhost:4201'){
        localStorage.setItem(this.storageService.TOKEN_KEY, e.data);
      }
    });
  }

}
