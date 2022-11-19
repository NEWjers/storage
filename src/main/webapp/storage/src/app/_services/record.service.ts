import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Record} from "../dto/Record";

const API_URL = 'http://localhost:8080/api/records';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class RecordService {

  constructor(private http: HttpClient) { }

  getAllItems() {
    return this.http.get<Record[]>(API_URL);
  }
}
