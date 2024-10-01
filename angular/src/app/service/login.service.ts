import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http:HttpClient) { }
  private apiUrl = 'http://localhost:8080/api';  // Backend API URL
  // Method for user forgot password
   forgotPassword(email: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/forgot-password`, { email });
  }

  // Method for user reset password
  resetPassword(token: string, newPassword: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/reset-password`, { token, newPassword });
  }

  // Method for admin forgot password
   forgotAdminPassword(email: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/admin/forgot-password`, { email });
  }

  // Method for admin reset password
  resetAdminPassword(token: string, newPassword: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/admin/reset-password`, { token, newPassword });
  }
}
