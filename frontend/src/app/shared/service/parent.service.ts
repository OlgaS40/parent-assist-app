import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ParentService {

  constructor(private _http: HttpClient) { }

  getAllParents(): Observable<any> {
    return this._http.get(`${environment.apiUrl}parents`);
  }
  getSubscription(id: string): Observable<any> {
    return this._http.get(`${environment.apiUrl}parents/${id}`);
  }

  createSubscription(data: any): Observable<any> {
    return this._http.post(`${environment.apiUrl}parents`, data);
  }

  updateSubscription(id: string, data: any): Observable<any> {
    return this._http.put(`${environment.apiUrl}parents/${id}`, data);
  }

  deleteSubscription(id: string): Observable<any> {
    return this._http.delete(`${environment.apiUrl}parents/${id}`);
  }
}
