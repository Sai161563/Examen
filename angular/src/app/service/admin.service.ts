import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Question } from '../model/question';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  private apiUrl='http://localhost:8080/api';


  constructor(private http:HttpClient) { }

  getQuestions(eid: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/admin/exams/${eid}/questions`);
  }

  deleteQuestion(qid: number): Observable<any> {
    console.log(qid);
    return this.http.delete(`${this.apiUrl}/questions/${qid}`);
  }
  
  getReports():Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/reports`);
  }

  addQuestion(question: Question) {
    return this.http.post<Question>(`${this.apiUrl}/questions`, question);
  }
}
