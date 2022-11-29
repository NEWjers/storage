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

  getMovingRecordsPage(pageNumber: number, pageSize: number, sort: string, way: string) {
    const params = {page: pageNumber, size: pageSize, sort: sort, way: way};
    return this.http.get<MovingRecordResponse[]>(API_URL + '/page', {params});
  }

  getMovingRecordsSize() {
    return this.http.get<number>(API_URL + '/size');
  }
}
