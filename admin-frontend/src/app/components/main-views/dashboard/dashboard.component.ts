import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { menuItems } from '../../../core/utils/menu-items';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {
  items: MenuItem[] = menuItems;
}
