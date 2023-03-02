import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(private _http: HttpClient) { }

  getAllUsers(): Observable<any>{
    return this._http.get(`${environment.apiUrl}users`);
  }
  getUser(id: string): Observable<any> {
    return this._http.get(`${environment.apiUrl}users/${id}`);
  }

  createUser(data: any): Observable<any> {
    return this._http.post(`${environment.apiUrl}users`, data);
  }

  updateUser(id: string, data: any): Observable<any> {
    return this._http.put(`${environment.apiUrl}users/${id}`, data);
  }

  deleteUser(id: string): Observable<any> {
    return this._http.delete(`${environment.apiUrl}users/${id}`);
  }
}
