import { Authority } from './authority';

export class User {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  password: string;
  enabled: boolean;
  activationLink: string;
  activationExpiration: Date;
  authorities: Authority[];
}
