import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { StorageService } from '../services/storage.service';
import { ADMIN, DOCTOR } from './constants';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private storageService: StorageService,
    private router: Router
  ){}

  canActivate(route: ActivatedRouteSnapshot): boolean {

    if (!this.storageService.getUser() && route.data.unauthorized) {
      return true;
    }

    for (const authority of route.data.authorities || []){
      if (this.storageService.getUser()?.authorities.includes(authority)){
        return true;
      }
    }

    if (this.storageService.getUser()?.authorities.includes(ADMIN)) {
      this.router.navigate([environment.reportRoute]);
    }
    else if (this.storageService.getUser()?.authorities.includes(DOCTOR)){
      this.router.navigate([environment.patientsRoute]);
    }
    else {
      this.router.navigate([environment.loginRoute]);
    }

    return false;
  }
}
