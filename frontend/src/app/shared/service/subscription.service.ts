import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  constructor(private _http: HttpClient) { }

  getAllSubscriptions(): Observable<any>{
    return this._http.get(`${environment.apiUrl}subscriptions`);
  }

  getSubscription(id: string): Observable<any> {
    return this._http.get(`${environment.apiUrl}subscriptions/${id}`);
  }

  createSubscription(data: any): Observable<any> {
    return this._http.post(`${environment.apiUrl}subscriptions`, data);
  }

  updateSubscription(id: string, data: any): Observable<any> {
    return this._http.put(`${environment.apiUrl}subscriptions/${id}`, data);
  }

  deleteSubscription(id: string): Observable<any> {
    return this._http.delete(`${environment.apiUrl}subscriptions/${id}`);
  }
}
