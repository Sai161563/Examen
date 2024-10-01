import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ExamInstructionsService {

  private apiUrl = 'http://localhost:8080/api/exams';

  constructor(private http:HttpClient) { }

  getExams(): Observable<any> {
    return this.http.get(this.apiUrl);
  }
}
