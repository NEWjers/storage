import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {MovingRecordResponse} from "../dto/MovingRecordResponse";

const API_URL = 'http://localhost:8080/api/moving-records';

@Injectable({
  providedIn: 'root'
})
export class MovingRecordService {

  constructor(private http: HttpClient) { }

  getAllMovingRecords() {
    return this.http.get<MovingRecordResponse[]>(API_URL);
  }
}
