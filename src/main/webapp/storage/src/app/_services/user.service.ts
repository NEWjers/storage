import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../dto/User';

const API_URL = 'http://localhost:8080/api/users';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getAllUsers(): Observable<any> {
    return this.http.get<User[]>(API_URL);
  }

  updateUser(id: number, username: string, role: string, password: string): Observable<any> {
    return this.http.post(API_URL, {
      id,
      username,
      role,
      password
    }, httpOptions);
  }

  deleteUser(id: number): Observable<any> {
    return this.http.delete(API_URL + '/' + id, httpOptions);
  }
}
