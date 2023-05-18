import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

const API_URL = `${environment.apiUrl}test/`;

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private _http: HttpClient) { }

  getPublicContent(): Observable<any> {
    return this._http.get(API_URL + 'all', { responseType: 'text' });
  }

  getUserBoard(): Observable<any> {
    return this._http.get(API_URL + 'user', { responseType: 'text' });
  }
  
  getContentMakerBoard(): Observable<any> {
    return this._http.get(API_URL + 'content_maker', { responseType: 'text' });
  }

  getAdminBoard(): Observable<any> {
    return this._http.get(API_URL + 'admin', { responseType: 'text' });
  }
}
