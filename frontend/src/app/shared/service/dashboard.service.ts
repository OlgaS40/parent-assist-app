import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private actuatorUrl = environment.apiUrl + '/actuator';

  constructor(private _http: HttpClient) { }

  getAppHealth():Observable<any>{
    return this._http.get<any>(`${this.actuatorUrl}/health`);
  }

  getHttpServerRequests():Observable<any>{
    return this._http.get<any>(`${this.actuatorUrl}/http.server.requests`);
  }
}
