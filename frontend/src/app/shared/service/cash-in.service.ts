import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class CashInService {

  constructor(private _http: HttpClient) { }

  getAllCashIn(): Observable<any> {
    return this._http.get(`${environment.apiUrl}cashIn`);
  }

  getCashIn(id: string): Observable<any> {
    return this._http.get(`${environment.apiUrl}cashIn/${id}`);
  }

  createCashIn(data: any): Observable<any> {
    return this._http.post(`${environment.apiUrl}cashIn`, data);
  }

  updateCashIn(id: string, data: any): Observable<any> {
    return this._http.put(`${environment.apiUrl}cashIn/${id}`, data);
  }

  deleteCashIn(id: string): Observable<any> {
    return this._http.delete(`${environment.apiUrl}cashIn/${id}`);
  }
}
