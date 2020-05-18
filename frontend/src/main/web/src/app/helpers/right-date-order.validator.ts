import {FormGroup} from '@angular/forms';

export function RightDateOrder(startDateControlName: string, endDateControlName: string) {
  return (formGroup: FormGroup) => {
    const startDateControl = formGroup.controls[startDateControlName];
    const endDateControl = formGroup.controls[endDateControlName];

    if (endDateControl.errors && !endDateControl.errors.dateOrder) {
      return;
    }

    if (startDateControl.value >= endDateControl.value) {
      endDateControl.setErrors({dateOrder: true});
    } else {
      endDateControl.setErrors(null);
    }
  }
}
