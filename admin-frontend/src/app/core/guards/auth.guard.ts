import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { AuthService } from '../services/auth.service';
import { SUPER_ADMIN } from '../utils/constants';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {

    if (!this.authService.getToken() && route.data.unauthorized) {
      return true;
    }

    for (const authority of route.data.authorities || []){
      if (this.authService.getToken()?.authorities.includes(authority)){
        return true;
      }
    }

    if (this.authService.getToken()?.authorities.includes(SUPER_ADMIN)) {
      this.router.navigate(['']);
    }
    else {
      this.router.navigate([environment.loginRoute]);
    }

    return false;
  }

}
