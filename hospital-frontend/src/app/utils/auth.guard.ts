import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { StorageService } from '../services/storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private storageService: StorageService,
    private router: Router
  ){}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const authorities = route.data.authorities as string[];
    for (const authority of authorities){
      if (this.storageService.getUser()?.authorities.includes(authority)){
        return true;
      }
    }
    this.router.navigate([environment.loginRoute]);
    return false;
  }
}
