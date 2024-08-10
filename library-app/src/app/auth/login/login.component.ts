import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styles: ``
})

export class LoginComponent {

  loginForm: FormGroup;
  message: string = '';

  constructor(
    private authService: AuthService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  login(): void {
    if (this.loginForm.valid) {
      const user = this.loginForm.value;
      this.authService.login(user).subscribe({
        next: (response) => {
          localStorage.setItem('token', response.body.token);
          this.router.navigate(['/dashboard/home']);
        },
        error: (error) => {
          this.message = 'Login failed: ' + error.error.error;
        }
      });
    } else {
      this.message = 'Rellene todos los campos.';
    }
  }
}
