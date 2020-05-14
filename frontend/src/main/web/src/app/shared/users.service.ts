import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {GET_ALL_USERS} from "../environments/environment";
import {map} from "rxjs/operators";
import {User} from "../model/user";

@Injectable({
  providedIn: 'root'
})

export class UsersService {
  users: User[];

  constructor(private http: HttpClient) {

  }

  getAllUsers() {
    return this.http.get<any>(`${GET_ALL_USERS}`).pipe(
      map(data => {
        if (data) {
          this.users = data;
        }
        return data;
      }));
  };
}
