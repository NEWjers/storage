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

  getSells() {
    return this.http.get<Sell[]>(API_URL);
  }

  getSellsPage(pageNumber: number, pageSize: number, sort: string, way: string) {
    const params = {page: pageNumber, size: pageSize, sort: sort, way: way};
    return this.http.get<Sell[]>(API_URL + '/page', {params});
  }

  getSellsSize() {
    return this.http.get<number>(API_URL + '/size');
  }

  createSell(sellRequests: SellRequest[]) {
    return this.http.request('POST', API_URL + "/new", {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      body: JSON.stringify(sellRequests)
    }, );
  }
}
