import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router} from '@angular/router';
import {AuthService} from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const authorities = route.data.authorities as string[];
    for (const authority of authorities){
      if (this.authService.getUser()?.authorities.includes(authority)){
        return true;
      }
    }
    this.router.navigate(['login']);
    return false;
  }
}
