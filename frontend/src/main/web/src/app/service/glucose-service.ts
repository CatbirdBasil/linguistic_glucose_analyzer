import {Injectable} from '@angular/core';
import {HttpClient, HttpEvent, HttpRequest} from "@angular/common/http";
import {map} from "rxjs/operators";
import {
  GET_GLUCOSE_RECORDS_FOR_CURR_USER_URL,
  GLUCOSE_ENDPOINT_URL,
  UPLOAD_GLUCOSE_RECORD_FROM_CSV_URL
} from "../environments/environment";
import {GlucoseDataRecord} from "@models/glucose-data-record";
import {Observable} from "rxjs";

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

  uploadFileWithRecords(file: File): Observable<HttpEvent<{}>> {
    const data: FormData = new FormData();
    data.append('file', file);
    const newRequest = new HttpRequest('POST', UPLOAD_GLUCOSE_RECORD_FROM_CSV_URL, data, {
      reportProgress: true,
      responseType: 'text'
    });
    return this.http.request(newRequest);
  }
}
