import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginService } from '../../service/login.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-password',
  standalone: true,
  imports: [FormsModule, CommonModule, ReactiveFormsModule],
  templateUrl: './admin-password.component.html',
  styleUrl: './admin-password.component.css'
})
export class AdminPasswordComponent {

  forgotPasswordForm: FormGroup;
  resetPasswordForm: FormGroup;
  tokenGenerated: boolean = false;
  resetToken: string = '';

  constructor(private fb: FormBuilder, private loginService: LoginService, private router:Router) {
    this.forgotPasswordForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
    });

    this.resetPasswordForm = this.fb.group({
      token: ['', Validators.required],
      newPassword: ['', [Validators.required, Validators.minLength(6)]],
    });
}
// Handle Forgot Password form submission
onForgotPasswordSubmit() {
  if (this.forgotPasswordForm.valid) {
    const email = this.forgotPasswordForm.get('email')?.value;
    this.loginService.forgotAdminPassword(email).subscribe(
      (response: any) => {
        console.log('Forgot Password Success:', response);

        // Ensure response contains the reset token
        if (response.token) {
          this.tokenGenerated = true;
          this.resetToken = response.token;  // Correctly assign the token
          alert(`Password reset token: ${this.resetToken}`); // Show pop-up with token
        } else {
          alert('No token received. Please try again.');
        }
      },
      (error: any) => {
        console.error('Forgot Password Error:', error);
        alert("Error: Email not found. Please try again.");
      }
    );
  }
}

// Handle Reset Password form submission
onResetPasswordSubmit() {
  if (this.resetPasswordForm.valid) {
    const token = this.resetPasswordForm.get('token')?.value;
    const newPassword = this.resetPasswordForm.get('newPassword')?.value;

    this.loginService.resetAdminPassword(token, newPassword).subscribe(
      (response: any) => {
        console.log('Password Reset Success:', response);
        alert('Password updated successfully!');
        this.router.navigate(['/admin-login'])
      },
      (error: any) => {
        console.error('Password Reset Error:', error);
      }
    );
  }
}
}
