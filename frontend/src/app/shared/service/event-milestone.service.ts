import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EventMilestoneService {

  constructor(private _http: HttpClient) { }
  
  getAllEventMilestones(): Observable<any>{
    return this._http.get(`${environment.apiUrl}eventMilestones`);
  }
  getEventMilestone(id: string): Observable<any> {
    return this._http.get(`${environment.apiUrl}eventMilestones/${id}`);
  }

  createEventMilestone(data: any): Observable<any> {
    return this._http.post(`${environment.apiUrl}eventMilestones`, data);
  }

  updateEventMilestone(id: string, data: any): Observable<any> {
    return this._http.put(`${environment.apiUrl}eventMilestones/${id}`, data);
  }

  deleteEventMilestone(id: string): Observable<any> {
    return this._http.delete(`${environment.apiUrl}eventMilestones/${id}`);
  }
}
