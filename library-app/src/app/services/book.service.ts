import { Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Book } from '../models/book.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class BookService {
  private apiUrl = 'http://localhost:8080/books';


  constructor(private http: HttpClient, private authService: AuthService) { }

  getAllBooks(): Observable<Book[]> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<Book[]>(`${this.apiUrl}/all`, { headers });
  }

  getBookByTitle(title: string): Observable<Book> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    })
    return this.http.get<Book>(`${this.apiUrl}/title?title=${title}`, { headers });
  }

  getBooksByCategory(category: string): Observable<Book[]> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    })
    return this.http.get<Book[]>(`${this.apiUrl}/category?category=${category}`, { headers });
  }

  getBooksByAuthor(authorId: number): Observable<Book[]> {
    return this.http.get<Book[]>(`${this.apiUrl}/author_id?author_id=${authorId}`);
  }

  saveBook(book: Book): Observable<Book> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    })
    return this.http.post<Book>(`${this.apiUrl}/save`, book, { headers });
  }

  deleteBook(id: number): Observable<void> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    })
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`, { headers });
  }

  updateBookTitle(id: number, updatedBook: Book): Observable<Book> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    })
    return this.http.put<Book>(`${this.apiUrl}/update/${id}`, updatedBook, { headers });
  }

}

