import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class ExamService {

  private apiUrl = 'http://localhost:8080/api'; // Replace with your API URL

  constructor(private http: HttpClient, private authService: AuthenticationService) {}

  getQuestions(eid: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/exams/${eid}/questions`);
  }

  submitExam(examName: string , answers: { [key: string]: string }): Observable<any> {
    var se = {
      "userId" : this.authService.getUserId(),
      "name" : examName,
      "answers" : answers
    };
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    console.log(se);
    return this.http.post<any>(`${this.apiUrl}/submit-exam`, se, { headers });
  }

  getReport(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/reports/user/${this.authService.getUserId()}`);
  }
}
