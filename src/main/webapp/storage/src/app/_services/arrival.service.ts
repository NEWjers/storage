import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Arrival} from "../dto/Arrival";

const API_URL = 'http://localhost:8080/api/arrivals';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ArrivalService {

  constructor(private http: HttpClient) { }

  getAllArrivals() {
    return this.http.get<Arrival[]>(API_URL);
  }
}
