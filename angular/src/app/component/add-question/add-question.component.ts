import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Exam } from '../../model/exam';
import { ExamInstructionsService } from '../../service/exam-instructions.service';
import { Question } from '../../model/question';
import { AdminService } from '../../service/admin.service';
import { AuthenticationService } from '../../service/authentication.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-add-question',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './add-question.component.html',
  styleUrl: './add-question.component.css'
})
export class AddQuestionComponent implements OnInit {

  questionForm: FormGroup;
  file: File | null = null;
  exams!: Exam[];

  constructor(private fb: FormBuilder,
    private examInstructionService: ExamInstructionsService,
    private adminService: AdminService,
    private authService:AuthenticationService,
    private router:Router) {
      if(!this.authService.isAdminLoggedIn()) {
        alert("You don't have access");
        this.router.navigate(['/']);
      }
    this.questionForm = this.fb.group({
      question: ['', Validators.required],
      optionA: ['', Validators.required],
      optionB: ['', Validators.required],
      optionC: ['', Validators.required],
      optionD: ['', Validators.required],
      correctAnswer: ['', Validators.required],
      examName: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.examInstructionService.getExams().subscribe((data: any) => {
      console.log(data);
      this.exams = data;
    });
  }

  onSubmit(): void {
    if (this.questionForm.valid) {
      const formValue = this.questionForm.value;
      const question: Question = {
        question: formValue.question,
        optionA: formValue.optionA,
        optionB: formValue.optionB,
        optionC: formValue.optionC,
        optionD: formValue.optionD,
        correctAnswer: formValue.correctAnswer,
        exam: {
          name: formValue.examName
        }
      };

      this.adminService.addQuestion(question).subscribe((data) => {
        if(data) {
          console.log('Question added successfully');
          this.questionForm.reset();
          alert("Question Added Successfully");
        }
        else {
          console.error('Error adding question');
          alert("An error Occured");
        }
      });
    }
  }

  onFileSelected(event: Event): void {
    const element = event.target as HTMLInputElement;
    const fileList: FileList | null = element.files;
    if (fileList) {
      this.file = fileList[0];
    }
  }

  uploadFile(): void {
    if (this.file) {
      const reader = new FileReader();
      reader.onload = (e: ProgressEvent<FileReader>) => {
        const text = e.target?.result as string;
        try {
          const question: Question = JSON.parse(text);
          this.adminService.addQuestion(question).subscribe((data) => {
            if(data) {
              console.log('Question added successfully');
              this.questionForm.reset();
              alert("Question Added Successfully");
            }
            else {
              console.error('Error adding question');
              alert("An error Occured");
            }
          });
        } catch (error) {
          console.error('Error parsing JSON', error);
        }
      };
      reader.readAsText(this.file);
    }
  }

  back(): void {
    this.router.navigate(['/admin']);
  }

}