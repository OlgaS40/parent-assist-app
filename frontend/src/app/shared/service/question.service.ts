import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  constructor(private _http: HttpClient) { }

  getAllQuestions(): Observable<any>{
    return this._http.get(`${environment.apiUrl}questions`);
  }

  getQuestion(id: string): Observable<any> {
    return this._http.get(`${environment.apiUrl}questions/${id}`);
  }

  createQuestion(data: any): Observable<any> {
    return this._http.post(`${environment.apiUrl}questions`, data);
  }

  updateQuestion(id: string, data: any): Observable<any> {
    return this._http.put(`${environment.apiUrl}questions/${id}`, data);
  }

  deleteQuestion(id: string): Observable<any> {
    return this._http.delete(`${environment.apiUrl}questions/${id}`);
  }
}
