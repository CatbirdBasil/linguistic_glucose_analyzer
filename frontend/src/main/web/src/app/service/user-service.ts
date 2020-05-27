import {Injectable} from "@angular/core";
import {HttpClient, HttpEvent, HttpRequest} from "@angular/common/http";
import {
  GET_USER_SUMMARY_URL
} from "@environments/environment";
import {UserSummary} from "@models/user-summary";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) {
  }

  public getCurrentUserData() {
    return this.http.get<UserSummary>(`${GET_USER_SUMMARY_URL}`);
  }

  public updateCurrentUserData(userSummary: UserSummary) {
    return this.http.put<UserSummary>(`${GET_USER_SUMMARY_URL}`, userSummary);
  }
}
