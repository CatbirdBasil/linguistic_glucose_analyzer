import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../model/user";
import {BehaviorSubject, Observable} from "rxjs";
import {map} from "rxjs/operators";
import {LOGIN_URL, REGISTRATION_URL} from "../environments/environment";
import * as jwt_decode from "jwt-decode";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentUserSubject: BehaviorSubject<User>;
  username: BehaviorSubject<string> = new BehaviorSubject(this.currentUsername);

  constructor(private http: HttpClient,
              private router: Router) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(this.token));
    this.username.next(this.currentUsername);
  }

  public get token() {
    return localStorage.getItem('currentUser');
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }

  registration(registrationPayload) {
    return this.http.post<any>(`${REGISTRATION_URL}`, registrationPayload).pipe(
      map(data => {
          return data;
        }
      ));
  }
  //
  // recoveryPassword(recoveryPasswordPayload) {
  //   return this.http.get<any>(`${PASSWORD_RECOVERY_URL}?email=` + recoveryPasswordPayload).pipe(
  //     map(data => {
  //         return data;
  //       }
  //     ));
  // }

  private get currentUsername() {
    if (this.token) {
      return this.getDecodedToken().username;
    } else {
      this.router.navigate(['login']);
    }
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  login(loginPayload) {
    return this.http.post<any>(`${LOGIN_URL}`, loginPayload).pipe(
      map(data => {
        if (data) {
          localStorage.setItem('currentUser', JSON.stringify(data.accessToken));
          this.currentUserSubject.next(data.accessToken);
          this.username.next(this.currentUsername);
        }
        return data;
      }));
  };

  getUsername() {
    return this.username.asObservable();
  }

  getDecodedToken() {
    try {
      return jwt_decode(this.token);
    } catch (Error) {
      return null;
    }
  }

  getTokenExpirationDate(token: string): Date {
    const decoded = jwt_decode(token);

    if (decoded.exp === undefined) return null;

    const date = new Date(0);
    date.setUTCSeconds(decoded.exp);
    return date;
  }

  isTokenExpired(): boolean {
    const date = this.getTokenExpirationDate(this.token);
    if (date === undefined) return true;
    return !(date.valueOf() > new Date().valueOf());
  }

}
