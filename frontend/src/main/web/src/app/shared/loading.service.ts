import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LoadingService {

  test: BehaviorSubject<boolean> = new BehaviorSubject(false);

  startLoading() {
    this.test.next(true);
  }

  stopLoading() {
    this.test.next(false);
  }
}
