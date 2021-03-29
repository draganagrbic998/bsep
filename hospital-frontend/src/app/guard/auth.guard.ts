import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { StorageService } from '../services/storage/storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private storageService: StorageService,
    private router: Router
  ){}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const roles = route.data.roles as string[];
    for (const role of roles){
      if (this.storageService.getUser()?.authorities.includes(role)){
        return true;
      }
    }
    this.router.navigate([environment.loginRoute]);
    return false;
  }
}
