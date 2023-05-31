import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, throwError } from "rxjs";
import { User } from "../model/user";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { catchError, map } from "rxjs/operators";
import { Router } from "@angular/router";
import { environment } from "../../../environments/environment";
import {SignUpRequest} from "../model/signUpRequest";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public user: Observable<User> | undefined;
  private userSubject!: BehaviorSubject<User>;

  constructor(private router: Router, private http:HttpClient) {
    const user = localStorage.getItem('user');
    if (user !== null) {
    this.userSubject = new BehaviorSubject<User>(
      (this.user = JSON.parse(localStorage.getItem("user") ?? ''))
    );
    this.user = this.userSubject.asObservable();
  }
   }


   public get userValue(): User {
    return this.userSubject.value;
  }

  login(user: User): Observable<User> {
    return this.http.post<User>(`${environment.apiUrl}auth/login/`, user).pipe(
      map((user) => {
        localStorage.setItem("user", JSON.stringify(user));
        this.userSubject.next(user);
        return user;
      })
    );
  }

  logout(): any {
    localStorage.removeItem("user");
    this.router.navigate(["/"]);
  }
  registerUser(signUpRequest: SignUpRequest) {
    return this.http.post(`${environment.apiUrl}/auth/signup/`, signUpRequest);
  }

  getAll(): Observable<User[]> {
    return this.http.get<User[]>(`${environment.apiUrl}user`);
  }

  getById(id: string): any {
    return this.http.get<User>(`${environment.apiUrl}/user/${id}`);
  }

  update(id: string, params: any): any {
    return this.http.put(`${environment.apiUrl}/user/${id}`, params).pipe(
      map((x) => {
        // update stored user if the logged in user updated their own record
        if (id == this.userValue.id) {
          // update local storage
          const user = { ...this.userValue, ...params };
          localStorage.setItem("user", JSON.stringify(user));

          // publish updated user to subscribers
          this.userSubject.next(user);
        }
        return x;
      })
    );
  }

  delete(id: string): any {
    return this.http.delete(`${environment.apiUrl}/user/${id}`).pipe(
      map((x) => {
        // auto logout if the logged in user deleted their own record
        if (id == this.userValue.id) {
          this.logout();
        }
        return x;
      })
    );
  }

  private sendRequest<T>(
    verb: string,
    url: string,
    body?: User
  ): Observable<T> {
    console.log("\n\n---Request ", verb, url, body);

    const myHeaders = new HttpHeaders({
      Accept: "application/json",
      "Content-Type": "application/json",
    });

    return this.http
      .request<T>(verb, url, {
        body,
        headers: myHeaders,
      })
      .pipe(
        catchError((error: Response) =>
          throwError(`Network Error: ${error.statusText} (${error.status})`)
        )
      );
  }
}
