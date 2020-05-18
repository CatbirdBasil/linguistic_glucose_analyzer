import {FormGroup} from '@angular/forms';

export function PercentageDiscount() {
  return (formGroup: FormGroup) => {
    const valueControl = formGroup.controls['amount'];
    const discountTypeControl = formGroup.controls['type'];

    if (valueControl.errors && !valueControl.errors.wrongPercentage) {
      return;
    }

    if (discountTypeControl.value == 'Percentage' && valueControl.value >= 100) {
      valueControl.setErrors({wrongPercentage: true});
    } else {
      valueControl.setErrors(null);
    }
  }
}
