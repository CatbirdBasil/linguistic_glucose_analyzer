import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../model/user";
import {BehaviorSubject, Observable} from "rxjs";
import {map} from "rxjs/operators";
import {LOGIN_URL, REGISTRATION_URL} from "../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;


  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  login(loginPayload) {
    console.log(loginPayload)
    return this.http.post<any>(`${LOGIN_URL}`, loginPayload).pipe(
      map(data => {
        if (data) {
          localStorage.setItem('currentUser', JSON.stringify(data));
          this.currentUserSubject.next(data);
        }
        return data;
      }));
  };

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }

  registration(registrationPayload) {
    console.log(registrationPayload);
    console.log("blin");
    return this.http.post<any>(`${REGISTRATION_URL}`, registrationPayload).pipe(
      map(data => {
          return data;
        }
      ));
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }
}
