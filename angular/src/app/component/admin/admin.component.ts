import { Component, OnInit } from '@angular/core';
import { ExamInstructionsService } from '../../service/exam-instructions.service';
import { Exam } from '../../model/exam';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthenticationService } from '../../service/authentication.service';


@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})
export class AdminComponent implements OnInit {

  exams!: Exam[];
  examRadio!: string;

  constructor(private examInstructionService: ExamInstructionsService,
    private router: Router,
    private authService: AuthenticationService) {};

  ngOnInit(): void {
    if(!this.authService.isAdminLoggedIn()) {
      alert("You don't have access");
      this.router.navigate(['/']);
    }
    this.examInstructionService.getExams().subscribe((data: any) => {
      console.log(data);
      this.exams = data;
    })
  }


  showQuestions() {
    console.log(this.examRadio);
    this.router.navigate(['/view-questions',this.examRadio]);
  }

  addQuestion() {
    this.router.navigate(['/add-questions'])
  }

  viewReport() {
    this.router.navigate(['/report-card', "any"]);
  }
}
