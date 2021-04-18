import { MenuItem } from 'primeng/api';
import { environment } from 'src/environments/environment';

export const menuItems: MenuItem[] =  [
  {
    label: 'Dashboard',
    routerLink: '/',
    routerLinkActiveOptions: {exact: true}
  },
  {
    label: 'Certificates',
    routerLink: `/${environment.certificatesRoute}`
  },
  {
    label: 'Users',
    routerLink: `/${environment.usersRoute}`
  },
  {
    label: 'Configuration',
    routerLink: `/${environment.configurationRoute}`
  }
];
