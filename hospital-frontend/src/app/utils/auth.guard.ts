import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { StorageService } from '../services/storage.service';
import { USER_ROLE } from './constants';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private storageService: StorageService,
    private router: Router
  ){}

  canActivate(route: ActivatedRouteSnapshot): boolean {

    if (route.data.unauthorized){
      if (!this.storageService.getToken()){
        return true;
      }
      if (!this.storageService.getToken()?.roles?.includes(USER_ROLE.ADMIN) &&
        !this.storageService.getToken()?.roles?.includes(USER_ROLE.DOCTOR)){
        return true;
      }
    }

    for (const role of route.data.roles || []){
      if (this.storageService.getToken()?.roles?.includes(role)){
        return true;
      }
    }

    if (this.storageService.getToken()?.roles?.includes(USER_ROLE.ADMIN)) {
      this.router.navigate([environment.reportRoute]);
    }
    else if (this.storageService.getToken()?.roles?.includes(USER_ROLE.DOCTOR)){
      this.router.navigate([environment.patientsRoute]);
    }
    else {
      this.storageService.removeToken();
      this.router.navigate([environment.loginRoute]);
    }

    return false;
  }
}
