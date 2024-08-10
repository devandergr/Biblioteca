import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-register',
  standalone: true,
  templateUrl: './register.component.html',
  imports: [FormsModule, ReactiveFormsModule],
  styles: ``
})
export class RegisterComponent {

  registerForm: FormGroup;
  message: string = '';

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.registerForm = this.formBuilder.group({
      first_name: ['', Validators.required],
      last_name: ['', Validators.required],
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  register(): void {
    if (this.registerForm.valid) {
      const user = this.registerForm.value;
      this.authService.register(user).subscribe({
        next: (response: HttpResponse<any>) => {
          if (response.status === 201) {
            this.message = '¡Registro exitoso!';
            setTimeout(() => {
              this.router.navigate(['auth/login']);
            }, 1500)
          } else {
            this.message = 'Registro fallido: ' + response.body;
          }
        },
        error: (err) => {
          this.message = 'Error en el registro: ' + err.error;
        }
      });
    } else {
      this.message = 'Rellene todos los campos.';
    }
  }
}
