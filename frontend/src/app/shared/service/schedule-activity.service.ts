import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ScheduleActivityService {

  constructor(private _http: HttpClient) { }

  getAllScheduleActivities(): Observable<any>{
    return this._http.get(`${environment.apiUrl}scheduleActivities`);
  }
  getSubscription(id: string): Observable<any> {
    return this._http.get(`${environment.apiUrl}scheduleActivities/${id}`);
  }

  createSubscription(data: any): Observable<any> {
    return this._http.post(`${environment.apiUrl}scheduleActivities`, data);
  }

  updateSubscription(id: string, data: any): Observable<any> {
    return this._http.put(`${environment.apiUrl}scheduleActivities/${id}`, data);
  }

  deleteSubscription(id: string): Observable<any> {
    return this._http.delete(`${environment.apiUrl}scheduleActivities/${id}`);
  }
}
