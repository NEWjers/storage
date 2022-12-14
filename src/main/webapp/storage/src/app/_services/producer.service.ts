import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { Producer } from '../dto/Producer';

const API_URL = 'http://localhost:8080/api/producers';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ProducerService {

  constructor(private http: HttpClient) { }

  getAllProducers() {
    return this.http.get<Producer[]>(API_URL);
  }

  getProducersPage(pageNumber: number, pageSize: number, sort: string, way: string, id: string, name: string,
                   country: string, description: string) {
    const params = {
      page: pageNumber,
      size: pageSize,
      sort: sort,
      way: way,
      id: id,
      name: name,
      country: country,
      description: description
    };
    return this.http.get<Producer[]>(API_URL + '/page', {params});
  }

  getProducersSize(id: string, name: string, country: string, description: string) {
    const params = {id: id, name: name, country: country, description: description};
    return this.http.get<number>(API_URL + '/size', {params});
  }

  getProducerReport(sort: string, way: string, id: string, name: string, country: string, description: string) {
    const params = {
      sort: sort,
      way: way,
      id: id,
      name: name,
      country: country,
      description: description
    };
    return this.http.get(API_URL + '/report', {params, responseType: 'blob' as 'json'});
  }

  createProducer(name: string, country: string, description: string) {
    return this.http.post(API_URL + "/new", {
      name,
      country,
      description
    }, httpOptions);
  }

  updateProducer(id: number, name: string, country: string, description: string): Observable<any> {
    return this.http.post(API_URL + "/update", {
      id,
      name,
      country,
      description
    }, httpOptions);
  }

  deleteProducer(id: number): Observable<any> {
    return this.http.delete(API_URL + '/' + id, httpOptions);
  }
}
