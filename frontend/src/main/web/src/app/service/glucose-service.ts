import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {GET_GLUCOSE_RECORDS_FOR_CURR_USER_URL, GLUCOSE_ENDPOINT_URL} from "../environments/environment";
import {GlucoseDataRecord} from "@models/glucose-data-record";

@Injectable({
  providedIn: 'root'
})
export class GlucoseService {
  constructor(private http: HttpClient) {
  }

  getGlucoseRecordsForCurrentUser() {
    return this.http.get<GlucoseDataRecord[]>(`${GET_GLUCOSE_RECORDS_FOR_CURR_USER_URL}`);
  }

  saveGlucoseRecord(recordPayload: GlucoseDataRecord) {
    return this.http.post<GlucoseDataRecord>(`${GLUCOSE_ENDPOINT_URL}`, recordPayload);
  }

  updateGlucoseRecord(recordPayload: GlucoseDataRecord) {
    return this.http.put<any>(`${GLUCOSE_ENDPOINT_URL}`, recordPayload);
  }

  deleteGlucoseRecord(recordId) {
    return this.http.delete<any>(`${GLUCOSE_ENDPOINT_URL}` + recordId)
  }
}
