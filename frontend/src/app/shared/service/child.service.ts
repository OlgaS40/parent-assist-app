import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChildService {

  constructor(private _http: HttpClient) { }
  
  getAllChildren(): Observable<any>{
    return this._http.get(`${environment.apiUrl}children`);
  }

  getChild(id: string): Observable<any> {
    return this._http.get(`${environment.apiUrl}children/${id}`);
  }

  createChild(data: any): Observable<any> {
    return this._http.post(`${environment.apiUrl}children`, data);
  }

  updateChild(id: string, data: any): Observable<any> {
    return this._http.put(`${environment.apiUrl}children/${id}`, data);
  }

  deleteChild(id: string): Observable<any> {
    return this._http.delete(`${environment.apiUrl}children/${id}`);
  }
}
