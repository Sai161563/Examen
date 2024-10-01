import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../service/authentication.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { User } from '../../model/user';

@Component({
  selector: 'app-user-registration',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './user-registration.component.html',
  styleUrls: ['./user-registration.component.css']
})
export class UserRegistrationComponent implements OnInit {
  user: User = new User();
  submitted = false;

  constructor(private authenticationService: AuthenticationService, private router: Router) { }

  ngOnInit(): void {
  }

  onSubmit() {
    this.submitted = true;
    this.user.id = 101;
    this.authenticationService.register(this.user).subscribe((data) => {
      if(data) {
        console.log(data);
        alert('User registered successfully!');
        this.router.navigate(['/user-login']); // navigate to login page after registration
      }
      else {
        alert('Error registering user!');
      }
    });
  }
}