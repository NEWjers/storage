import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Sell} from "../dto/Sell";
import {SellRequest} from "../dto/SellRequest";

const API_URL = 'http://localhost:8080/api/sells';

@Injectable({
  providedIn: 'root'
})
export class SellService {

  constructor(private http: HttpClient) { }

  getSellsPage(pageNumber: number, pageSize: number, sort: string, way: string, id: string, date: string, user: string) {
    const params = {page: pageNumber, size: pageSize, sort: sort, way: way, id: id, date: date, user: user};
    return this.http.get<Sell[]>(API_URL + '/page', {params});
  }

  getSellsSize(id: string, date: string, user: string) {
    const params = {id: id, date: date, user: user};
    return this.http.get<number>(API_URL + '/size', {params});
  }

  getSellNewReport(sellRequests: SellRequest[]) {
    return this.http.request('POST', API_URL + '/report', {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      body: JSON.stringify(sellRequests),
      responseType: 'blob' as 'json'
    });
  }

  getSellExistedReport(id: number) {
    const params = {id: id};
    return this.http.get(API_URL + '/report', {params, responseType: 'blob' as 'json'});
  }

  createSell(sellRequests: SellRequest[]) {
    return this.http.request('POST', API_URL + "/new", {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      body: JSON.stringify(sellRequests)
    }, );
  }
}
