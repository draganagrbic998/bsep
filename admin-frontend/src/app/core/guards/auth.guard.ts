import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { StorageService } from '../services/storage.service';
import { USER_ROLE } from '../utils/constants';
import {Role} from '../model/role';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private storageService: StorageService,
    private router: Router
  ) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {

    if (route.data.unauthorized){
      if (!this.storageService.getToken()){
        return true;
      }
      if (!this.storageService.getToken().roles?.some((r: Role) => r.name === USER_ROLE.SUPER_ADMIN)){
        return true;
      }
    }

    for (const role of route.data.roles || []){
      if (this.storageService.getToken()?.roles?.some((r: Role) => r.name === role)){
        return true;
      }
    }

    if (this.storageService.getToken()?.roles?.some((r: Role) => r.name === USER_ROLE.SUPER_ADMIN)) {
      this.router.navigate(['']);
    }
    else {
      this.storageService.removeToken();
      this.router.navigate([environment.loginRoute]);
    }

    return false;
  }

}
