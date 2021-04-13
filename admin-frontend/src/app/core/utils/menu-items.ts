import {MenuItem} from 'primeng/api';

export const menuItems: MenuItem[] =  [
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
  },
  {
    label: 'Configuration',
    routerLink: '/configuration'
  }
];
