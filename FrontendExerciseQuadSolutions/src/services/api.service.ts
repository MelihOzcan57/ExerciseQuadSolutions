import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private baseUrl = 'http://localhost:8080/api/exercise'; // Your API base URL

  constructor(private http: HttpClient) { }

  // Define methods for different API endpoints
  getQuestions(): Observable<any> {
    return this.http.get(`${this.baseUrl}/questions`);
  }

  // You can add more methods for POST, PUT, DELETE, etc.
}
