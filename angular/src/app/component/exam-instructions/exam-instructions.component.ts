import { Component, OnInit } from '@angular/core';
import { Exam } from '../../model/exam';
import { ExamInstructionsService } from '../../service/exam-instructions.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthenticationService } from '../../service/authentication.service';

@Component({
  selector: 'app-exam-instructions',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './exam-instructions.component.html',
  styleUrl: './exam-instructions.component.css'
})
export class ExamInstructionsComponent implements OnInit{
  exams: Exam[] = [];
  examRadio: any;

  constructor(private examInstructionService: ExamInstructionsService,
    private router: Router,
    private authService: AuthenticationService) {
      if(!this.authService.isLoggedIn()) {
        alert("You don't have access");
        this.router.navigate(['/']);
      }
    }

  ngOnInit(): void {
      this.examInstructionService.getExams().subscribe((data: any) => {
        console.log(data);
        this.exams = data;
      })
  }

  startExam(){
    console.log(this.examRadio);
    this.router.navigate(['/exam',this.examRadio]);
  }
}
