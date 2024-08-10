import { Component, OnInit } from '@angular/core';
import { BookService } from '../../../services/book.service';
import { Book } from '../../../models/book.model';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import { UserService } from '../../../services/user.service';

@Component({
  selector: 'app-book',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './books.component.html',
})

export class BookComponent implements OnInit{

  books: any[] = [];
  book: Book | null = null;
  registerForm: FormGroup;
  isAdmin = false;
  searchQuery = '';

  constructor(
    private bookService: BookService,
    private formBuilder: FormBuilder,
    private userService: UserService
  ) { 
    this.registerForm = this.formBuilder.group({
      title: ['', Validators.required],
      isbn: ['', Validators.required],
      publisher: ['', Validators.required],
      year_publication: ['', Validators.required],
      num_pages: ['', Validators.required],
      category: ['', Validators.required],
      available: ['', Validators.required],
      
    });
  }

  getAllBooks(): void {
    this.bookService.getAllBooks().subscribe(books => {
      this.books = books;
    });
  }

  ngOnInit(): void {
    this.getUserDetails();
  }

  getUserDetails(): void {
    this.userService.getUserDetails().subscribe(user => {
      this.isAdmin = user.userType === 'ADMIN';
    });
  }

  getBookByTitle(title: string): void {
    this.bookService.getBookByTitle(title).subscribe((book: Book) => {
      if (book) {
        this.books = [book];
      } else {
        this.books = [];
      }
    });
  }

  getBooksByCategory(category: string): void {
    this.bookService.getBooksByCategory(category).subscribe(books => {
      this.books = books;
    });
  }

  getBooksByAuthor(authorId: number): void {
    this.bookService.getBooksByAuthor(authorId).subscribe(books => {
      this.books = books;
    });
  }

  saveBook(book: any): void {
    if(this.registerForm.valid) {
        this.bookService.saveBook(book).subscribe(() => {
        console.log('Libro guardado con éxito');
        this.registerForm.reset();
      });
    }
  }

  deleteBook(id: number): void {
    this.bookService.deleteBook(id).subscribe(() => {
      console.log('Libro eliminado con éxito');
      this.getAllBooks();
    });
  }

  updateBookTitle(id: number, updatedBook: any): void {
    this.bookService.updateBookTitle(id, updatedBook).subscribe(() => {
      console.log('Libro actualizado con éxito');
    });
  }

  onSearch(event: Event): void {
    const InputEvent = event as InputEvent;
    const input = InputEvent?.target as HTMLInputElement;
    const query = input.value;
    this.searchQuery = query;
    if (query) {
      // Busca por título
      this.getBookByTitle(query);
    } else {
      // Si la búsqueda está vacía, mostrar todos los libros
      this.getAllBooks();
    }
  }

}
