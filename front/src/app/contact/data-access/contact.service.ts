import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { ContactMessage } from "./contact.model";
import { Observable, of, delay } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class ContactService {
    constructor(private http: HttpClient) {}

    sendMessage(message: ContactMessage): Observable<boolean> {
        // Simulation d'un appel API
        // Dans un vrai projet, remplacer par un vrai appel HTTP
        return of(true).pipe(delay(1000));
    }
  }
