import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RelationshipService {

  constructor(private _http: HttpClient) { }

  getAllRelationships(){
    return this._http.get(`${environment.apiUrl}relationships`);
  }
  getRelationship(id: string): Observable<any> {
    return this._http.get(`${environment.apiUrl}relationships/${id}`);
  }

  createRelationship(data: any): Observable<any> {
    return this._http.post(`${environment.apiUrl}relationships`, data);
  }

  updateRelationship(id: string, data: any): Observable<any> {
    return this._http.put(`${environment.apiUrl}relationships/${id}`, data);
  }

  deleteRelationship(id: string): Observable<any> {
    return this._http.delete(`${environment.apiUrl}relationships/${id}`);
  }
}
