import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private _http: HttpClient) { }

  getAllProducts(): Observable<any>{
    return this._http.get(`${environment.apiUrl}products`);
  }

  getProduct(id: string): Observable<any> {
    return this._http.get(`${environment.apiUrl}products/${id}`);
  }

  createProduct(data: any): Observable<any> {
    return this._http.post(`${environment.apiUrl}products`, data);
  }

  updateProduct(id: string, data: any): Observable<any> {
    return this._http.put(`${environment.apiUrl}products/${id}`, data);
  }

  deleteProduct(id: string): Observable<any> {
    return this._http.delete(`${environment.apiUrl}products/${id}`);
  }
}
