import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { LoanDTO } from '../models/loan.model';

@Injectable({
  providedIn: 'root'
})
export class LoanService {
  private apiUrl = 'http://localhost:8080/loans';

  constructor(private http: HttpClient, private authService: AuthService) { }

  createLoan(username: string, bookId: number): Observable<string> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.post<string>(`${this.apiUrl}/create`, null, {
      headers,
      params: { username, bookId }
    });
  }

  deleteLoan(id: number): Observable<void> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    console.log(`Deleting with id: ${id}`); 
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers });
  }

  getUserLoans(username: string): Observable<LoanDTO[]> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<LoanDTO[]>(`${this.apiUrl}/my-loans`, {
      headers,
      params: { username }
    });
  }

  getActiveUsers(): Observable<any[]> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<LoanDTO[]>(`${this.apiUrl}/active-users`, { headers });
  }
}
