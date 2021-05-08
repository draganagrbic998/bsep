import {Role} from './role';

export interface AuthToken{
    id: number;
    token: string;
    roles: Role[];
    authorities: string[];
}

