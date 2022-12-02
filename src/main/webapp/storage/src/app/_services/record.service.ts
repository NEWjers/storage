import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Record} from "../dto/Record";

const API_URL = 'http://localhost:8080/api/records';

@Injectable({
  providedIn: 'root'
})
export class RecordService {

  constructor(private http: HttpClient) { }

  getRecordsPage(pageNumber: number, pageSize: number, sort: string, way: string, id:string, code: string,
                 itemSize: string, pack: string, price: string, description: string, producer: string, count: string) {
    const params = {
      page: pageNumber,
      size: pageSize,
      sort: sort,
      way: way,
      id: id,
      code: code,
      itemSize: itemSize,
      pack: pack,
      price: price,
      description: description,
      producer: producer,
      count: count
    };
    return this.http.get<Record[]>(API_URL + '/page', {params});
  }

  getRecordsSize(id:string, code: string, itemSize: string, pack: string, price: string, description: string,
                 producer: string, count: string) {

    const params = {
      id: id,
      code: code,
      itemSize: itemSize,
      pack: pack,
      price: price,
      description: description,
      producer: producer,
      count: count
    };
    return this.http.get<number>(API_URL + '/size', {params});
  }
}
