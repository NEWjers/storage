import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Arrival} from "../dto/Arrival";
import {ArrivalRequest} from "../dto/ArrivalRequest";

const API_URL = 'http://localhost:8080/api/arrivals';

@Injectable({
  providedIn: 'root'
})
export class ArrivalService {

  constructor(private http: HttpClient) { }

  getArrivalsPage(pageNumber: number, pageSize: number, sort: string, way: string, id: string, date: string, user: string) {
    const params = {page: pageNumber, size: pageSize, sort: sort, way: way, id: id, date: date, user: user};
    return this.http.get<Arrival[]>(API_URL + '/page', {params});
  }

  getArrivalsSize(id: string, date: string, user: string) {
    const params = {id: id, date: date, user: user};
    return this.http.get<number>(API_URL + '/size', {params});
  }

  getArrivalNewReport(arrivalRequests: ArrivalRequest[]) {
    return this.http.request('POST', API_URL + '/report', {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      body: JSON.stringify(arrivalRequests),
      responseType: 'blob' as 'json'
    });
  }

  createArrival(arrivalRequests: ArrivalRequest[]) {
    return this.http.request('POST', API_URL + "/new", {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      body: JSON.stringify(arrivalRequests)
    }, );
  }
}
