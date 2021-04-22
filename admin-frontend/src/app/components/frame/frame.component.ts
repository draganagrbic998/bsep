import { Component } from '@angular/core';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-frame',
  templateUrl: './frame.component.html',
  styleUrls: ['./frame.component.scss']
})
export class FrameComponent {

  constructor(private authService: AuthService) {
    window.addEventListener('message', e => {
      console.log(e.data, e.origin);
      if (e.origin === 'https://localhost:4201'){
        localStorage.setItem(this.authService.TOKEN_KEY, e.data);
      }
    });
  }

}
