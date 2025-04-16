import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { environment } from '../../../environment/environment';

interface AuthResponse {
  token: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = environment.apiUrl; // Utilisation de l'URL de l'environnement
  private userSubject = new BehaviorSubject<{ token: string } | null>(null);

  constructor(private http: HttpClient) {
    const token = localStorage.getItem('token');
    if (token) {
      this.userSubject.next({ token });
    }
  }

  // Inscription d'un utilisateur
  register(userData: any): Observable<AuthResponse | null> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/register`, userData).pipe(
      tap((response) => {
        if (response.token) {
          localStorage.setItem('token', response.token);
          this.userSubject.next({ token: response.token });
        }
      }),
      catchError((error) => {
        console.error('Erreur lors de l\'inscription', error);
        return of(null);  // Renvoie un Observable avec null en cas d'erreur
      })
    );
  }

  // Connexion d'un utilisateur
  login(credentials: { email: string; password: string }): Observable<AuthResponse | null> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/token`, credentials).pipe(
      tap((response) => {
        if (response.token) {
          localStorage.setItem('token', response.token);
          this.userSubject.next({ token: response.token });
        }
      }),
      catchError((error) => {
        console.error('Erreur lors de la connexion', error);
        return of(null);  // Renvoie un Observable avec null en cas d'erreur
      })
    );
  }

  // Déconnexion
  logout(): void {
    localStorage.removeItem('token');
    this.userSubject.next(null);
  }

  // Vérifie si l'utilisateur est authentifié
  isAuthenticated(): boolean {
    return !!this.userSubject.value?.token;
  }

  // Retourne le token de l'utilisateur
  getToken(): string | null {
    return this.userSubject.value?.token || null;
  }
}
