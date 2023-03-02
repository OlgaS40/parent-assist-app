import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ActivityService {

  constructor(private _http: HttpClient) { }
  
  getAllActivities(): Observable<any>{
    return this._http.get(`${environment.apiUrl}activities`);
  }

  getActivity(id: string): Observable<any> {
    return this._http.get(`${environment.apiUrl}activities/${id}`);
  }

  createActivity(data: any): Observable<any> {
    return this._http.post(`${environment.apiUrl}activities`, data);
  }

  updateActivity(id: string, data: any): Observable<any> {
    return this._http.put(`${environment.apiUrl}activities/${id}`, data);
  }

  deleteActivity(id: string): Observable<any> {
    return this._http.delete(`${environment.apiUrl}activities/${id}`);
  }
}
