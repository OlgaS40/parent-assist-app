import {Injectable} from "@angular/core";
import {BehaviorSubject, Observable, throwError} from "rxjs";
import {User} from "../model/user";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, map} from "rxjs/operators";
import {Router} from "@angular/router";
import {environment} from "../../../environments/environment";
import {SignUpRequest} from "../model/signUpRequest";
import {LoginRequest} from "../model/loginRequest";
import {ForgotPasswordRequest} from "../model/forgotPasswordRequest";
import {Oauth2SignInUpRequest} from "../model/oauth2SignInUpRequest";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public user: Observable<User | null> | undefined;
  isLoggedIn = false;
  private userSubject!: BehaviorSubject<User | null>;

  constructor(private router: Router, private _http: HttpClient) {
    const user = localStorage.getItem('user');
    if (user !== null) {
      this.userSubject = new BehaviorSubject<User | null>(
        (this.user = JSON.parse(localStorage.getItem("user") ?? ''))
      );
      this.user = this.userSubject.asObservable();
    }
  }

  public get userValue(): User | null {
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

  signUpWithGoogle(googleSignUpRequest: Oauth2SignInUpRequest) {
    return this._http.post(`${environment.apiUrl}auth/signup/google`, googleSignUpRequest).pipe(
      map(
        response => {
          return response;
        }
      )
    )
  }

  signUpWithFacebook(facebookSignUpRequest: Oauth2SignInUpRequest) {
    return this._http.post(`${environment.apiUrl}auth/signup/facebook`, facebookSignUpRequest);
  }

  login(loginRequest: LoginRequest, keepMeSignIn: boolean): Observable<User> {
    return this._http.post<User>(`${environment.apiUrl}auth/signin`, loginRequest).pipe(
      map((user) => {
        if (keepMeSignIn) {
          localStorage.setItem("user", JSON.stringify(user));
          if (this.userSubject !== undefined) {
            this.userSubject.next(user);
          } else {
            this.userSubject = new BehaviorSubject<User | null>(user);
          }
        }
        this.isLoggedIn = true
        return user;
      })
    );
  }

  loginWithGoogle(googleSignInUpRequest: Oauth2SignInUpRequest) {
    return this._http.post<User>(`${environment.apiUrl}auth/signin/google`, googleSignInUpRequest).pipe(
      map((user) => {
          localStorage.setItem("user", JSON.stringify(user));
          if (this.userSubject !== undefined) {
            this.userSubject.next(user);
          } else {
            this.userSubject = new BehaviorSubject<User | null>(user);
          }
          this.isLoggedIn = true
          return user;
        }
      )
    )
  }

  loginWithFacebook(facebookLoginRequest: Oauth2SignInUpRequest) {
    return this._http.post<User>(`${environment.apiUrl}auth/signin/facebook`, facebookLoginRequest).pipe(
      map((user) => {
          localStorage.setItem("user", JSON.stringify(user));
          if (this.userSubject !== undefined) {
            this.userSubject.next(user);
          } else {
            this.userSubject = new BehaviorSubject<User | null>(user);
          }
          this.isLoggedIn = true
          return user;
        }
      )
    )
  }

  logout(): any {
    localStorage.removeItem("user");
    this.isLoggedIn = false;
    this.userSubject.next(null);
    this.router.navigate(['/']).then();
  }

  forgotPassword(forgotPasswordRequest: ForgotPasswordRequest) {
    return this._http.post(`${environment.apiUrl}auth/forgotPassword`, forgotPasswordRequest);
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
        if (this.userValue !== null && id == this.userValue.id) {
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
        if (this.userValue !== null && id == this.userValue.id) {
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
