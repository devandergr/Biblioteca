import { Component, OnInit } from '@angular/core';
import { routes } from '../../app.routes';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { UserService } from '../../services/user.service';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'app-sidemenu',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './sidenav.component.html',
})
export class SidemenuComponent implements OnInit {
  public menuItems = routes
    .map((route) => route.children ?? [])
    .flat()
    .filter((route) => route && route.path)
    .filter((route) => !route.path?.includes(':'))
    .map(route => ({
      path: route.path,
      title: route.title,
      icon: this.sanitizer.bypassSecurityTrustHtml(route.data?.['icon'] || '') // Usa DomSanitizer
    }));

  constructor(private authService: AuthService, private router: Router, private userService: UserService, private sanitizer: DomSanitizer) {}

  username: string = '';

  ngOnInit(): void {
    this.userService.getUserDetails().subscribe({
      next: user => {
        this.username = user.username;
      },
      error: error => {
        console.error('Error fetching user details', error);
      },
      complete: () => {
      }
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/auth/login']);
  }
}