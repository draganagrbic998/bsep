import {Component} from '@angular/core';
import {AuthService} from '../../../core/services/auth.service';
import {Router} from '@angular/router';
import {MenuItem} from 'primeng/api';

@Component({
  selector: 'app-main-view',
  templateUrl: './main-view.component.html',
  styleUrls: ['./main-view.component.scss']
})
export class MainViewComponent {

  items: MenuItem[] = [
    {
      label: 'Dashboard',
      routerLink: '/',
      routerLinkActiveOptions: {exact: true}
    },
    {
      label: 'Certificates',
      routerLink: '/certificates'
    },
    {
      label: 'Users',
      routerLink: '/users'
    }
  ];

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['login']);
  }

}
