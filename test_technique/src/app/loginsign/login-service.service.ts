import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LoginServiceService {
  private baseUrl = environment.apiUrl;

  // `BehaviorSubject` pour surveiller l'état de connexion
  private loggedInSubject = new BehaviorSubject<boolean>(this.isLoggedIn());
  loggedIn$ = this.loggedInSubject.asObservable();

  constructor(private http: HttpClient) {}

  register(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/register`, user, {
      headers: { 'Content-Type': 'application/json' },
    });
  }

  login(email: string, password: string): Observable<any> {
    const body = { email, password };
    return this.http.post(`${this.baseUrl}/auth/login`, body);
  }

  saveUserData(user: any, token: string): void {
    if (this.isBrowser()) {
      localStorage.setItem('user', JSON.stringify(user));
      localStorage.setItem('token', token);
      this.loggedInSubject.next(true); // Mise à jour de l'état connecté
    }
  }

  getUserData(): any {
    if (this.isBrowser()) {
      const user = localStorage.getItem('user');
      return user ? JSON.parse(user) : null;
    }
    return null;
  }

  isLoggedIn(): boolean {
    if (this.isBrowser()) {
      return !!localStorage.getItem('token');
    }
    return false;
  }

  logout(): void {
    if (this.isBrowser()) {
      localStorage.removeItem('user');
      localStorage.removeItem('token');
      this.loggedInSubject.next(false); // Mise à jour de l'état déconnecté
    }
  }

  private isBrowser(): boolean {
    return typeof window !== 'undefined';
  }
}
