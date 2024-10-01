import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { ExamService } from '../../service/exam.service';
import { AuthenticationService } from '../../service/authentication.service';



@Component({
  selector: 'app-exam',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './exam.component.html',
  styleUrl: './exam.component.css'
})
export class ExamComponent implements OnInit {
  questions : any[] = [];
  examId: number = 0;
  examName: string = '';
  answers: { [key: string]: string } = {};
  

  constructor(private route:ActivatedRoute,
    private router: Router,
    private examService: ExamService,
    private authService: AuthenticationService) {
    if(!this.authService.isLoggedIn()) {
      alert("You don't have access");
      this.router.navigate(['/']);
    }
  }

  ngOnInit(): void {
    var examId = this.route.snapshot.paramMap.get('eid');
    this.examId = +(examId?examId:0);
    console.log(this.examId);
    this.examService.getQuestions(this.examId).subscribe((data:any) => {
      this.examName = data.examName;
      this.questions = data.questions;
      console.log(this.questions);
      console.log(this.examName);
    })
  }

  submitExam() {
    console.log(this.answers);
    this.examService.submitExam(this.examName,this.answers).subscribe((data) => {
      console.log(data);
      this.router.navigate(['/report-card', JSON.stringify(data)]);
    })
  }

}
