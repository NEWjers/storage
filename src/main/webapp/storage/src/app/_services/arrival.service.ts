import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Arrival} from "../dto/Arrival";
import {ArrivalRequest} from "../dto/ArrivalRequest";

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

  createArrival(arrivalRequests: ArrivalRequest[]) {
    return this.http.request('POST', API_URL + "/new", {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      body: JSON.stringify(arrivalRequests)
    }, );
  }
}
