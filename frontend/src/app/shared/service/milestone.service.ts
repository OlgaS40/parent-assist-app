import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MilestoneService {

  constructor(private _http: HttpClient) { }

  getAllMilestones(): Observable<any>{
    return this._http.get(`${environment.apiUrl}milestones`);
  }

  getMilestone(id: string): Observable<any> {
    return this._http.get(`${environment.apiUrl}milestones/${id}`);
  }

  createMilestone(data: any): Observable<any> {
    return this._http.post(`${environment.apiUrl}milestones`, data);
  }

  updateMilestone(id: string, data: any): Observable<any> {
    return this._http.put(`${environment.apiUrl}milestones/${id}`, data);
  }

  deleteMilestone(id: string): Observable<any> {
    return this._http.delete(`${environment.apiUrl}milestones/${id}`);
  }
}
