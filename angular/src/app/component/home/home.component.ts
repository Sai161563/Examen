import { Component } from '@angular/core';
import { AuthenticationService } from '../../service/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  constructor(private authService: AuthenticationService,
    private router: Router) {
      if(this.authService.isAdminLoggedIn()) {
        this.router.navigate(['/admin']);
      }
    }

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  onReportClick() {
    if (this.isLoggedIn()) {
      this.router.navigate(['/report-card', 'all']);
    } else {
      alert('Please login first');
      this.router.navigate(['/user-login']);
    }
  }

  getUser() {
    return this.authService.getUserName();
  }

  onNewExamClick() {
    if (this.isLoggedIn()) {
      this.router.navigate(['/exam-instructions']);
    } else {
      alert('Please login first');
      this.router.navigate(['/user-login']);
    }
  }
}
