import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SidemenuComponent } from '../sidenav/sidenav/sidenav.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [RouterOutlet, SidemenuComponent],
  templateUrl: './dashboard.component.html',
  styles: ``
})
export class DashboardComponent  {

}
