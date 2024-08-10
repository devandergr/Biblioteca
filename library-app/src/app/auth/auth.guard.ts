import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    if (this.authService.isAuthenticated()) {
        console.log('User is authenticated');
        return true;
    } else {
        console.log('User is not authenticated, redirecting to login');
        this.router.navigate(['/auth/login']);
      return false;
    }
  }
}
