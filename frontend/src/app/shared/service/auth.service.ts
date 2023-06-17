import {HostListener, Injectable} from "@angular/core";
import {BehaviorSubject, Observable, throwError} from "rxjs";
import {User} from "../model/user";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, map} from "rxjs/operators";
import {Router} from "@angular/router";
import {environment} from "../../../environments/environment";
import {SignUpRequest} from "../model/signUpRequest";
import {LoginRequest} from "../model/loginRequest";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public user: Observable<User> | undefined;
  isLoggedIn = false;
  private userSubject!: BehaviorSubject<User>;

  constructor(private router: Router, private _http: HttpClient) {
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

  registerUser(signUpRequest: SignUpRequest) {
    const url = window.location.href;
    const headers = new HttpHeaders()
      .set('X-URL', url);
    console.log(url);
    return this._http.post(`${environment.apiUrl}auth/signup`, signUpRequest, {headers});
  }

  verifyRegistration(verificationCode: string) {
    return this._http.get(`${environment.apiUrl}auth/signup/verify?code=${verificationCode}`);
  }

  login(loginRequest: LoginRequest, keepMeSignIn: boolean): Observable<User> {
    return this._http.post<User>(`${environment.apiUrl}auth/signin`, loginRequest).pipe(
      map((user) => {
        if (keepMeSignIn) {
          localStorage.setItem("user", JSON.stringify(user));
          if(this.userSubject !== undefined){
            this.userSubject.next(user);
          } else {
            this.userSubject = new BehaviorSubject<User>(user);
          }
        }
        this.isLoggedIn = true
        return user;
      })
    );
  }

  logout(): any {
    localStorage.removeItem("user");
    this.isLoggedIn = false;
    this.router.navigate(['/']);
  }

  getAll(): Observable<User[]> {
    return this._http.get<User[]>(`${environment.apiUrl}user`);
  }

  getById(id: string): any {
    return this._http.get<User>(`${environment.apiUrl}/user/${id}`);
  }

  update(id: string, params: any): any {
    return this._http.put(`${environment.apiUrl}/user/${id}`, params).pipe(
      map((x) => {
        // update stored user if the logged in user updated their own record
        if (id == this.userValue.id) {
          // update local storage
          const user = {...this.userValue, ...params};
          localStorage.setItem("user", JSON.stringify(user));

          // publish updated user to subscribers
          this.userSubject.next(user);
        }
        return x;
      })
    );
  }

  delete(id: string): any {
    return this._http.delete(`${environment.apiUrl}/user/${id}`).pipe(
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

    return this._http
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
