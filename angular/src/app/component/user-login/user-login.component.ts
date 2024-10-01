import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AuthenticationService } from '../../service/authentication.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-user-login',
  standalone: true,
  imports: [CommonModule,FormsModule,RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.css']
})
export class UserLoginComponent {
  email: string = '';
  password: string = '';

  constructor(private authService: AuthenticationService, private router: Router) {}

  onSubmit() {
    this.authService.userLogin(this.email, this.password).subscribe(
      success => {
        console.log(success);
        if (success) {
          this.router.navigate(['/']);
        } else {
          alert('Invalid credentials');
        }
      },
      error => {
        console.error(error);
        alert('User Not Found...');
      }
    );
  }
}