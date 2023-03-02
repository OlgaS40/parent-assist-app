import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EventTestService {

  constructor(private _http: HttpClient) { }
  
  getAllEventTests(): Observable<any>{
    return this._http.get(`${environment.apiUrl}eventTests`);
  }

  getEventTest(id: string): Observable<any> {
    return this._http.get(`${environment.apiUrl}eventTests/${id}`);
  }

  createEventTest(data: any): Observable<any> {
    return this._http.post(`${environment.apiUrl}eventTests`, data);
  }

  updateEventTest(id: string, data: any): Observable<any> {
    return this._http.put(`${environment.apiUrl}eventTests/${id}`, data);
  }

  deleteEventTest(id: string): Observable<any> {
    return this._http.delete(`${environment.apiUrl}eventTests/${id}`);
  }
}
