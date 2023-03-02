import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EventActivityService {

  constructor(private _http: HttpClient) { }
  
  getAllEventActivities(){
    return this._http.get(`${environment.apiUrl}eventActivities`);
  }

  getEventActivity(id: string): Observable<any> {
    return this._http.get(`${environment.apiUrl}eventActivities/${id}`);
  }

  createEventActivity(data: any): Observable<any> {
    return this._http.post(`${environment.apiUrl}eventActivities`, data);
  }

  updateEventActivity(id: string, data: any): Observable<any> {
    return this._http.put(`${environment.apiUrl}eventActivities/${id}`, data);
  }

  deleteEventActivity(id: string): Observable<any> {
    return this._http.delete(`${environment.apiUrl}eventActivities/${id}`);
  }
}
