import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthenticationService } from '../../service/authentication.service';
import { Router } from '@angular/router';

interface Student {
  id: number;
  fname: string;
  email: string;
  mobileNo: string;
  dob: string;
  city: string;
  qualification: string;
  state: string;
  year: string;
  password: string;
}

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css'
})

export class UserProfileComponent implements OnInit {
  
    student!: Student;
  
    editMode: boolean = false;
    studentForm: FormGroup;
  
    constructor(private fb: FormBuilder, private authService: AuthenticationService, private router:Router) {

      if(!this.authService.isLoggedIn()) {
        alert("You don't have access");
        this.router.navigate(['/']);
      }
      this.studentForm = this.fb.group({
        fname: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        mobileNo: ['', [Validators.required, Validators.pattern("^[0-9]{10}$")]],
        dob: ['', Validators.required],
        city: ['', Validators.required],
        qualification: ['', Validators.required],
        state: ['', Validators.required],
        year: ['', [Validators.required, Validators.pattern("^[0-9]{4}$")]]
      });
    }
  
    ngOnInit(): void {
      this.authService.getUserDetails().subscribe((data) => {
        if(data) {
          this.student = data;
        }
      });
      // this.populateForm();
    }
  
    toggleEditMode(): void {
      this.editMode = !this.editMode;
      if (this.editMode) {
        this.populateForm();
      }
    }
  
    populateForm(): void {
      this.studentForm.patchValue({
        fname: this.student.fname,
        email: this.student.email,
        mobileNo: this.student.mobileNo,
        dob: this.student.dob,
        city: this.student.city,
        qualification: this.student.qualification,
        state: this.student.state,
        year: this.student.year
      });
    }
  
    updateStudent(): void {
      if (this.studentForm.valid) {
        this.student = {
          ...this.student,
          ...this.studentForm.value
        };
        this.authService.updateUser(this.student).subscribe((data) => {
          if(data) {
            alert("Profile updated successfully")
          }
          else {
            alert("Profile not updated: Details mismatched");
            this.authService.getUserDetails().subscribe((data) => {
              if(data) {
                this.student = data;
              }
            });
          }
        });
        this.editMode = false;
        // Here you would typically call a service to update the student information on the server
        console.log('Updated student:', this.student);
      }
    }
  }