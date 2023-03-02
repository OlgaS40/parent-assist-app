import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment"
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MaterialsToysService {

  constructor(private _http: HttpClient) { }

  getAllMaterialsToys(){
    return this._http.get(`${environment.apiUrl}materialsToys`);
  }

  getMaterialsToy(id: string): Observable<any> {
    return this._http.get(`${environment.apiUrl}materialsToys/${id}`);
  }

  createMaterialsToy(data: any): Observable<any> {
    return this._http.post(`${environment.apiUrl}materialsToys`, data);
  }

  updateMaterialsToy(id: string, data: any): Observable<any> {
    return this._http.put(`${environment.apiUrl}materialsToys/${id}`, data);
  }

  deleteMaterialsToy(id: string): Observable<any> {
    return this._http.delete(`${environment.apiUrl}materialsToys/${id}`);
  }
}
