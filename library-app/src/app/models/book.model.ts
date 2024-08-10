import { Author } from './author.model';

export interface Book {
  id: number;
  title: string;
  isbn: string;
  publisher: string;
  yearPublication: string;
  numPages: number;
  category: string;
  available: number;
  authors: Author[];
}
