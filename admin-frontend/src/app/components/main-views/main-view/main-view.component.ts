import { Component } from '@angular/core';
import { StorageService } from '../../../core/services/storage.service';
import { Router } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { menuItems } from '../../../core/utils/menu-items';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-main-view',
  templateUrl: './main-view.component.html',
  styleUrls: ['./main-view.component.scss']
})
export class MainViewComponent {

  items: MenuItem[] = menuItems;

  constructor(
    private storageService: StorageService,
    private router: Router
  ) { }

  signOut(): void {
    this.storageService.removeToken();
    this.router.navigate([environment.loginRoute]);
  }

}
