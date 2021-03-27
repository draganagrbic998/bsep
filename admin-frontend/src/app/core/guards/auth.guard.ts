import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import { Observable } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';
import {AuthService} from '../services/auth.service';
import {Admin} from '../model/admin';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  private jwtHelper: JwtHelperService = new JwtHelperService();

  constructor(private authService: AuthService,
              private router: Router) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const admin = this.authService.admin.getValue();
    const token: string | null = this.authService.token.getValue();
    const isTokenExpired: boolean = !!token ? this.jwtHelper.isTokenExpired(token) : true;
    const isAuthenticated = !isTokenExpired && !!admin.id;

    let accessRoles: string[] = [];
    if (route.data.hasOwnProperty('roles')) {
      accessRoles = route.data.roles as string[];
    } else {
      return true;
    }
    if (accessRoles.length === 0) {
      return false;
    }
    if (!isAuthenticated && accessRoles.includes('UNREGISTERED')) {
      return true;
    } else if (!isAuthenticated && !accessRoles.includes('UNREGISTERED')) {
      this.router.navigate(['login']);
      return false;
    } else {
      const auth = this.authorize(accessRoles, admin) ?? false;
      if (!auth) {
        this.router.navigate(['']);
        return false;
      }

      return true;
    }
  }

  private authorize(accessRoles: string[], admin: Admin | null): boolean {

    if (!admin) {
      return false;
    }
    return true; // accessRoles.includes(admin.role); something else
  }

}
