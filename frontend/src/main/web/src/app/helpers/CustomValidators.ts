import {isString} from "util";
import {FormControl} from "@angular/forms";

export class CustomValidators {
  static required(control: FormControl) {
    return (isString(control.value) && control.value.trim() == "") ?
      {"required": true} :
      null;
  }

  static minLength(control: FormControl, minLength: number) {
    return (isString(control.value) && control.value.trim().length < minLength) ?
      {"minLength": true} :
      null;
  }

  static startDate(control: FormControl, editMode: boolean = false) {
    return (new Date(control.value) < new Date(Date.now())) ?
      {"startDate": true} :
      null;
  }
}
