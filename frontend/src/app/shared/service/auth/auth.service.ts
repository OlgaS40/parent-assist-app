import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { User } from "../../model/user";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { environment } from "../../../../environments/environment";

const AUTH_API = `${environment.apiUrl}auth/`;

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private _http:HttpClient) {}
  
  login(username: string, password: string): Observable<any> {
    return this._http.post(
      AUTH_API + 'signin',
      {
        username,
        password,
      },
      httpOptions
    );
  }

  register(user: any): Observable<any> {
    return this._http.post(AUTH_API + 'signup', user);
  }

  logout(): any {
    return this._http.post(AUTH_API + 'signout', { }, httpOptions);
  }

  // public user: Observable<User> | undefined;
  // private userSubject!: BehaviorSubject<User>;
  // public get userValue(): User {
  //   return this.userSubject.value;
  // }

  // getAll(): Observable<User[]> {
  //   return this.http.get<User[]>(`${environment.apiUrl}user`);
  // }

  // getById(id: string): any {
  //   return this.http.get<User>(`${environment.apiUrl}/user/${id}`);
  // }

  // update(id: string, params: any): any {
  //   return this.http.put(`${environment.apiUrl}/user/${id}`, params).pipe(
  //     map((x) => {
  //       // update stored user if the logged in user updated their own record
  //       if (id == this.userValue.id) {
  //         // update local storage
  //         const user = { ...this.userValue, ...params };
  //         localStorage.setItem("user", JSON.stringify(user));

  //         // publish updated user to subscribers
  //         this.userSubject.next(user);
  //       }
  //       return x;
  //     })
  //   );
  // }

  // delete(id: string): any {
  //   return this.http.delete(`${environment.apiUrl}/user/${id}`).pipe(
  //     map((x) => {
  //       // auto logout if the logged in user deleted their own record
  //       if (id == this.userValue.id) {
  //         this.logout();
  //       }
  //       return x;
  //     })
  //   );
  // }

  // private sendRequest<T>(
  //   verb: string,
  //   url: string,
  //   body?: User
  // ): Observable<T> {
  //   console.log("\n\n---Request ", verb, url, body);

  //   const myHeaders = new HttpHeaders({
  //     Accept: "application/json",
  //     "Content-Type": "application/json",
  //   });

  //   return this.http
  //     .request<T>(verb, url, {
  //       body,
  //       headers: myHeaders,
  //     })
  //     .pipe(
  //       catchError((error: Response) =>
  //         throwError(`Network Error: ${error.statusText} (${error.status})`)
  //       )
  //     );
  }
