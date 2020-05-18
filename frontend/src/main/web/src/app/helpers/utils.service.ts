import {ElementRef, Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class Utils {

  constructor() {
  }

  static scrollToBottom(element: ElementRef) {
    try {
      // setTimeout(() => {}, 100);
      element.nativeElement.scrollTop = element.nativeElement.scrollHeight;
    } catch (err) {
      /**
       * catch errors with scroll which going beyond its max value
       * no need to handle or log smth
       */
    }
  }
}
