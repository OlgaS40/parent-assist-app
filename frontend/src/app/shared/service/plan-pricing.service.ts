import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PlanPricingService {

  constructor(private _http: HttpClient) { }

  getAllPlanPricing(): Observable<any>{
    return this._http.get(`${environment.apiUrl}planPricing`);
  }
  getPlanPricing(id: string): Observable<any> {
    return this._http.get(`${environment.apiUrl}planPricing/${id}`);
  }

  createPlanPricing(data: any): Observable<any> {
    return this._http.post(`${environment.apiUrl}planPricing`, data);
  }

  updatePlanPricing(id: string, data: any): Observable<any> {
    return this._http.put(`${environment.apiUrl}planPricing/${id}`, data);
  }

  deletePlanPricing(id: string): Observable<any> {
    return this._http.delete(`${environment.apiUrl}planPricing/${id}`);
  }
}
