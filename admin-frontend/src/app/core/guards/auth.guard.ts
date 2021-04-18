import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { environment } from 'src/environments/environment';
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
    const isLoggedIn = !!this.authService.getToken();
    const unauthorized = !!route.data.unauthorized;
    const authorities = route.data.authorities as string[] || [];

    if (!isLoggedIn && unauthorized) {
      return true;
    }

    for (const authority of authorities){
      if (this.authService.getToken()?.authorities.includes(authority)){
        return true;
      }
    }

    if (isLoggedIn) {
      this.router.navigate(['']);
    }
    else {
      this.router.navigate([environment.loginRoute]);
    }

    return false;
  }

}
