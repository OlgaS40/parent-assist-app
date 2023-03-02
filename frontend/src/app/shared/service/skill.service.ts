import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SkillService {

  constructor(private _http: HttpClient) { }

  getAllSkills(){
    return this._http.get(`${environment.apiUrl}skills`);
  }
  getSubscription(id: string): Observable<any> {
    return this._http.get(`${environment.apiUrl}skills/${id}`);
  }

  createSubscription(data: any): Observable<any> {
    return this._http.post(`${environment.apiUrl}skills`, data);
  }

  updateSubscription(id: string, data: any): Observable<any> {
    return this._http.put(`${environment.apiUrl}skills/${id}`, data);
  }

  deleteSubscription(id: string): Observable<any> {
    return this._http.delete(`${environment.apiUrl}skills/${id}`);
  }
}
