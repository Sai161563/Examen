import { Component } from '@angular/core';
import { AuthenticationService } from '../../service/authentication.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin-login',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './admin-login.component.html',
  styleUrl: './admin-login.component.css'
})
export class AdminLoginComponent {

  email: string = '';
  password: string = '';

  constructor(private authService: AuthenticationService, private router: Router) {}

  onSubmit() {
    console.log(this.email);
    this.authService.adminLogin(this.email, this.password).subscribe(
      success => {
        if (success) {
          this.router.navigate(['/admin']);
        } else {
          alert('Invalid credentials');
        }
      },
      error => {
        console.error('Error:', error);
        alert('Admin Not Found...');
      }
    );
  }
}