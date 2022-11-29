import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Item } from '../dto/Item';

const API_URL = 'http://localhost:8080/api/items';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  constructor(private http: HttpClient) { }

  getAllItems() {
    return this.http.get<Item[]>(API_URL);
  }

  getItemsPage(pageNumber: number, pageSize: number, sort: string, way: string) {
    const params = {page: pageNumber, size: pageSize, sort: sort, way: way};
    return this.http.get<Item[]>(API_URL + '/page', {params});
  }

  getItemsSize() {
    return this.http.get<number>(API_URL + '/size');
  }

  createItem(code: string, size: string, pack: number, price: number, description: string, producerId: number) {
    return this.http.post(API_URL + "/new", {
      code,
      size,
      pack,
      price,
      description,
      producerId
    }, httpOptions);
  }

  updateItem(id: number, code: string, size: string, pack: number, price: number, description: string, producerId: number) {
    return this.http.post(API_URL + "/update", {
      id,
      code,
      size,
      pack,
      price,
      description,
      producerId
    }, httpOptions);
  }

  deleteItem(id: number) {
    return this.http.delete(API_URL + '/' + id, httpOptions);
  }
}
