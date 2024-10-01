import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../service/authentication.service';
import { CommonModule } from '@angular/common';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {
  isAdmin: boolean = false;
  isLoggedIn: boolean = false;
  userName: string = "Examen";

  constructor(private authService: AuthenticationService, private router: Router) { }

  ngOnInit(): void {
    this.updateAuthStatus();
  }

  updateAuthStatus(): void {
    this.authService.userName$.subscribe((userName: string) => {
      if(userName) {
        this.isAdmin = this.authService.isAdminLoggedIn();
        this.isLoggedIn = this.authService.isLoggedIn();
        this.userName = userName;
      }
      else {
        this.userName = "Examen";
      }
    });
  }

  logout() {
    this.authService.logout();
  }

}
