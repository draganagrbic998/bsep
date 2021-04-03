import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const isLoggedIn: boolean = this.authService.isLoggedIn();
    const authorities = !route.data.authorities ? [] : route.data.authorities as string[];
    const unauthorized = route.data.unauthorized as boolean;

    if (unauthorized && !isLoggedIn) {
      return true;
    }

    for (const authority of authorities){
      if (this.authService.getUser()?.authorities.includes(authority)){
        return true;
      }
    }

    if (isLoggedIn) {
      this.router.navigate(['']);
    } else {
      this.router.navigate(['login']);
    }
    return false;
  }
}
