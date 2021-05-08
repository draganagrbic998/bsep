import {Role} from './role';

export class AuthToken {
  id: number;
  token: string;
  roles: Role[];
  authorities: string[];
}
