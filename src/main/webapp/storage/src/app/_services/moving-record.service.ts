import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {MovingRecordResponse} from "../dto/MovingRecordResponse";

const API_URL = 'http://localhost:8080/api/moving-records';

@Injectable({
  providedIn: 'root'
})
export class MovingRecordService {

  constructor(private http: HttpClient) { }

  getMovingRecordsPage(pageNumber: number, pageSize: number, sort: string, way: string, date: string, user: string,
                       movingType: string, code: string, itemSize: string, pack: string, price: string,
                       description: string, producer: string, count: string) {
    const params = {
      page: pageNumber,
      size: pageSize,
      sort: sort,
      way: way,
      date: date,
      user: user,
      movingType: movingType,
      code: code,
      itemSize: itemSize,
      pack: pack,
      price: price,
      description: description,
      producer: producer,
      count: count
    };
    return this.http.get<MovingRecordResponse[]>(API_URL + '/page', {params});
  }

  getMovingRecordsSize(date: string, user: string, movingType: string, code: string, itemSize: string, pack: string,
                       price: string, description: string, producer: string, count: string) {
    const params = {
      date: date,
      user: user,
      movingType: movingType,
      code: code,
      itemSize: itemSize,
      pack: pack,
      price: price,
      description: description,
      producer: producer,
      count: count
    }
    return this.http.get<number>(API_URL + '/size', {params});
  }

  getHistoryReport(sort: string, way: string, date: string, user: string, movingType: string, code: string,
                   itemSize: string, pack: string, price: string, description: string, producer: string, count: string) {

    const params = {
      sort: sort,
      way: way,
      date: date,
      user: user,
      movingType: movingType,
      code: code,
      itemSize: itemSize,
      pack: pack,
      price: price,
      description: description,
      producer: producer,
      count: count
    };

    return this.http.get(API_URL + '/report', {params, responseType: 'blob' as 'json'});
  }
}
