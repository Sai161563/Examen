import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AdminService } from '../../service/admin.service';
import { AuthenticationService } from '../../service/authentication.service';

@Component({
  selector: 'app-view-questions',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './view-questions.component.html',
  styleUrl: './view-questions.component.css'
})
export class ViewQuestionsComponent implements OnInit {

  questions : any[] = [];
  examId: number = 0;
  examName: string = '';
  expandedQuestions: Set<number> = new Set();

  constructor(private route:ActivatedRoute,
    private router: Router,
    private adminService: AdminService,
    private authService: AuthenticationService) {
      if(!this.authService.isAdminLoggedIn()) {
        alert("You don't have access");
        this.router.navigate(['/']);
      }
    }

  ngOnInit(): void {
    var examId = this.route.snapshot.paramMap.get('eid');
    this.examId = +(examId?examId:0);
    console.log(this.examId);
    this.adminService.getQuestions(this.examId).subscribe((data:any) => {
      this.examName = data.examName;
      this.questions = data.questions;
      console.log(this.questions);
      console.log(this.examName);
    })
  }

  toggleQuestion(qid: number): void {
    if (this.expandedQuestions.has(qid)) {
      this.expandedQuestions.delete(qid);
    } else {
      this.expandedQuestions.add(qid);
    }
    console.log(qid);
  }

  deleteQuestion(qid: number): void {
    this.adminService.deleteQuestion(qid).subscribe((data) => {
      console.log(data);
      if(data) {
        this.questions = this.questions.filter(q => q.qid !== qid);
        console.log(qid);
        this.expandedQuestions.delete(qid);
      }
      else {
        console.log("Not deleted");
      }
    });
  }

  isExpanded(qid: number): boolean {
    return this.expandedQuestions.has(qid);
  }
  
}
