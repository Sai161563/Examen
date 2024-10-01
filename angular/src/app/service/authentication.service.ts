import { HttpClient,HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable,throwError, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { BehaviorSubject } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

private apiUrl='http://localhost:8080/api';

private userIdSubject = new BehaviorSubject<string>('');
userId$ = this.userIdSubject.asObservable();
private userNameSubject = new BehaviorSubject<string>('');
userName$ = this.userNameSubject.asObservable();


constructor(private http:HttpClient) {
  if (typeof window !== 'undefined') { // Check if window is available
    const userId = sessionStorage.getItem('userId');
    if (userId) {
      this.userIdSubject.next(userId);
      this.userNameSubject.next(sessionStorage.getItem('userName') as string);
    }
  }
}

  userLogin(email: string, password: string): Observable<boolean> {
    console.log(email);
    console.log(password);
    return this.http.post<any>(`${this.apiUrl}/users/login`, { email, password }).pipe(
      map(response => {
        if (response != 0) {
          sessionStorage.setItem('userId', response.userId);
          sessionStorage.setItem('userName', response.userName);
          this.userIdSubject.next(response.userId);
          this.userNameSubject.next(response.userName);
          return true;
        }
        return false;
      })
    );
  }

  adminLogin(email: string, password: string): Observable<boolean> {
    return this.http.post<any>(`${this.apiUrl}/admin/login`, { email, password }).pipe(
      map(response => {
        if (response) {
          sessionStorage.setItem('userId', 'admin');
          sessionStorage.setItem('userName', 'Admin');
          this.userIdSubject.next('admin');
          this.userNameSubject.next('Admin');
          return true;
        }
        return false;
      })
    );
  }

  register(user: any): Observable<boolean> {
    return this.http.post<any>(`${this.apiUrl}/users/register`, {
      u_id: user.id,
      city: user.city,
      dob: user.dob,
      email: user.email,
      fname: user.fname,
      mobileNo: user.mobileNo,
      password: user.password,
      qualification: user.qualification,
      state: user.state,
      year: user.year
    });
  }

  forgotPassword(email: string): Observable<boolean> {
    return this.http.post<any>(`${this.apiUrl}/forgetpass`, { email }).pipe(
      map(response => {
        return response.success;
      })
    );
  }

  verifyAndUpdatePassword(email: string, verificationCode: string, newPassword: string): Observable<boolean> {
    return this.http.post<any>(`${this.apiUrl}/verify-update-password`, { email, verificationCode, newPassword }).pipe(
      map(response => {
        return response.success;
      })
    );
  }

  getUserName(): string | null {
    return this.userNameSubject.value;
  }

  updateUser(student:any):Observable<Boolean> {
    return this.http.put<Boolean>(`${this.apiUrl}/users/${student.id}`,student);
  }

  getUserDetails(): Observable<any> {
    return this.http.get<any[]>(`${this.apiUrl}/users/${this.getUserId()}`);
  }

  getUserId(): string | null {
    return this.userIdSubject.value;
  }

  isAdminLoggedIn(): boolean {
    if (typeof window !== 'undefined') {
      let token=sessionStorage.getItem('userId');
      console.log('UserId :'+token);
      return token !== null && token === 'admin';
    } 
    return false;
  }

  isLoggedIn(): boolean {
    if (typeof window !== 'undefined') {
      let token=sessionStorage.getItem('userId');
      console.log('UserId :'+token);
      return token !== null;
    } 
    return false;
  }

  logout(): void {
    if (typeof window !== 'undefined') {
      sessionStorage.removeItem('userId');
    }
  }
}
