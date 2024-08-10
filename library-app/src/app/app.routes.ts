import { Routes } from '@angular/router';
import { AuthGuard } from './auth/auth.guard';

export const routes: Routes = [
    {
        path: 'auth/register',
        loadComponent: async () => (await import('./auth/register/register.component')).RegisterComponent
    },
    {
        path:'auth/login',
        loadComponent: async () => (await import('./auth/login/login.component')).LoginComponent
    },
    {
        path: 'dashboard', redirectTo: 'dashboard/home', pathMatch: 'full'
    },
    {
        path: 'dashboard',
        loadComponent: async() => (await import('./dashboard/dashboard.component')).DashboardComponent,
        canActivate: [AuthGuard],
        children: [
            {
                path: 'home',
                title: 'Inicio',
                data: {icon: '<svg class="w-6 h-6 text-gray-800 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 24 24"><path fill-rule="evenodd" d="M11.293 3.293a1 1 0 0 1 1.414 0l6 6 2 2a1 1 0 0 1-1.414 1.414L19 12.414V19a2 2 0 0 1-2 2h-3a1 1 0 0 1-1-1v-3h-2v3a1 1 0 0 1-1 1H7a2 2 0 0 1-2-2v-6.586l-.293.293a1 1 0 0 1-1.414-1.414l2-2 6-6Z" clip-rule="evenodd"/></svg>'},
                loadComponent: async () => (await import('./dashboard/pages/home/home.component')).HomeComponent,
                canActivate: [AuthGuard],
            },
            {
                path: 'books',
                title: 'Libros',
                data: { icon: '<svg class="w-6 h-6 text-gray-800 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 24 24"><path fill-rule="evenodd" d="M6 2a2 2 0 0 0-2 2v15a3 3 0 0 0 3 3h12a1 1 0 1 0 0-2h-2v-2h2a1 1 0 0 0 1-1V4a2 2 0 0 0-2-2h-8v16h5v2H7a1 1 0 1 1 0-2h1V2H6Z" clip-rule="evenodd"/></svg>'},
                //data: {menuItem: {title: 'Libros', icon: '<svg class="w-6 h-6 text-gray-800 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 24 24"><path fill-rule="evenodd" d="M6 2a2 2 0 0 0-2 2v15a3 3 0 0 0 3 3h12a1 1 0 1 0 0-2h-2v-2h2a1 1 0 0 0 1-1V4a2 2 0 0 0-2-2h-8v16h5v2H7a1 1 0 1 1 0-2h1V2H6Z" clip-rule="evenodd"/></svg>'}},
                loadComponent: async () => (await import('./dashboard/pages/books/books.component')).BookComponent,
                canActivate: [AuthGuard],
            },
            {
                path: 'loans',
                title: 'Prestamos',
                data: { icon: '<svg class="w-6 h-6 text-gray-800 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 24 24"><path d="M13.849 4.22c-.684-1.626-3.014-1.626-3.698 0L8.397 8.387l-4.552.361c-1.775.14-2.495 2.331-1.142 3.477l3.468 2.937-1.06 4.392c-.413 1.713 1.472 3.067 2.992 2.149L12 19.35l3.897 2.354c1.52.918 3.405-.436 2.992-2.15l-1.06-4.39 3.468-2.938c1.353-1.146.633-3.336-1.142-3.477l-4.552-.36-1.754-4.17Z"/></svg>'}, 
                loadComponent: async () => (await import('./dashboard/pages/loans/loans.component')).LoansComponent,
                canActivate: [AuthGuard],
            },
        ]
    },
    {
        path: '', redirectTo: 'auth/login', pathMatch: 'full'
    }
];
