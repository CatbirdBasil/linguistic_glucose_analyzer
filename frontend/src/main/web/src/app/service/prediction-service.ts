import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {GET_PREDICTION_FOR_CURR_USER_URL} from "../environments/environment";
import {PossibleGlucoseValue} from "@models/possible-glucose-value";

@Injectable({
  providedIn: 'root'
})
export class PredictionService {
  constructor(private http: HttpClient) {
  }

  getPossibleFutureGlucoseValue() {
    return this.http.get<PossibleGlucoseValue>(`${GET_PREDICTION_FOR_CURR_USER_URL}`);
  }
}
