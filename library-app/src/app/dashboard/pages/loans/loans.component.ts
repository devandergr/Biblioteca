import { Component, OnInit } from '@angular/core';
import { LoanService } from '../../../services/loan.service';
import { UserService } from '../../../services/user.service';
import { LoanDTO } from '../../../models/loan.model';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-loans',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './loans.component.html',
})
export class LoansComponent implements OnInit {
  username1: string = '';
  registerForm: FormGroup;
  activeUsers: LoanDTO[] = [];
  loans: LoanDTO[] = [];
  isAdmin = false;

  constructor(private loanService: LoanService, private userService: UserService, private formBuilder: FormBuilder,) {
    this.registerForm = this.formBuilder.group({
      username: ['', Validators.required],
      bookid: ['', Validators.required], 
    });
  }

  ngOnInit(): void {
    this.getUserDetails();
  }

  getUserDetails(): void {
    this.userService.getUserDetails().subscribe(user => {
      this.isAdmin = user.userType === 'ADMIN';
      this.username1 = user.username; 
      this.loadLoans();
    });
  }

  loadLoans(): void {
    if (this.isAdmin) {
      this.getActiveUsers(); // Obtener préstamos activos de todos los usuarios
    } else {
      this.getUserLoans(); // Obtener préstamos del usuario logueado
    }
  }

  createLoan(): void {
    if (this.registerForm.valid) {
      const formValues = this.registerForm.value;
      const username = formValues.username;
      const bookId = formValues.bookid;

      console.log(`Creating loan with username: ${username} and bookid: ${bookId}`); // Verifica los valores

      this.loanService.createLoan(username, bookId).subscribe({
        next: (response) => {
          console.log(response);
          this.loadLoans(); // Recargar la lista de préstamos después de crear uno nuevo
          this.registerForm.reset(); // Resetear los campos del formulario
        },
        error: (error) => console.error(error),
        complete: () => console.log('Loan created'),
      });
    }
  }

  deleteLoan(id: number): void {
    console.log(`Deleting loan with id: ${id}`); // Verifica el valor del id
    if (id != null && id !== undefined) {
      this.loanService.deleteLoan(id).subscribe({
        next: () => {
          this.loadLoans(); // Recargar la lista de préstamos después de eliminar
        },
        error: (error) => {
          console.error('Error deleting loan:', error);
        }
      });
    } else {
      console.error('Invalid loan id:', id);
    }
  }

  getActiveUsers(): void {
    this.loanService.getActiveUsers().subscribe({
      next: (response: LoanDTO[]) => {
        console.log(response);
        this.activeUsers = response;
        this.loans = response; // Asegurando que loans se llene con la respuesta
      },
      error: (error) => console.error(error),
      complete: () => console.log('Active users loaded'),
    });
  }

  getUserLoans(): void {
    this.loanService.getUserLoans(this.username1).subscribe({
      next: (response: LoanDTO[]) => {
        this.loans = response;
      },
      error: (error) => console.error(error),
      complete: () => console.log('User loans loaded'),
    });
  }
}
