import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';

import {AuthService} from "@services/auth.service";

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(
    private router: Router,
    private authService: AuthService
  ) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const currentUser = this.authService.currentUserValue;
    const currentUserRole = this.authService.getDecodedToken().authority;
    if (currentUser) {

      if (route.data.roles && route.data.roles.indexOf(currentUserRole) === -1) {
        // role not authorised so redirect to home page
        this.router.navigate(['/']);
        return false;
      }

      // logged in so return true
      return true;
    }


    // not logged in so redirect to login page with the return url
    this.router.navigate(['/login']);
    return false;
  }
}
