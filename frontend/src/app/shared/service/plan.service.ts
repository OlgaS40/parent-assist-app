import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PlanService {

  constructor(private _http: HttpClient) { }

  getAllPlans(): Observable<any>{
    return this._http.get(`${environment.apiUrl}plans`);
  }
  getPlan(id: string): Observable<any> {
    return this._http.get(`${environment.apiUrl}plans/${id}`);
  }

  createPlan(data: any): Observable<any> {
    return this._http.post(`${environment.apiUrl}plans`, data);
  }

  updatePlan(id: string, data: any): Observable<any> {
    return this._http.put(`${environment.apiUrl}plans/${id}`, data);
  }

  deletePlan(id: string): Observable<any> {
    return this._http.delete(`${environment.apiUrl}plans/${id}`);
  }
}
