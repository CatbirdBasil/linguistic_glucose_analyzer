import {Pipe, PipeTransform} from '@angular/core';
import {CurrencyPipe, DatePipe} from "@angular/common";
import {ColumnMap} from "./layout.model";

@Pipe({name: 'formatCell'})
export class FormatCellPipe implements PipeTransform {
  constructor(private currencyPipe: CurrencyPipe,
              private datePipe: DatePipe,) {
  }

  transform(value: any, formatting: ColumnMap) {
    if (value === undefined) {
      return 'not available';
    }

    // if (formatting.type === 'default' || formatting.type === null) {
    //   if (typeof value === "object") {
    //     if (Array.isArray(value)) {
    //       let hasObjects = value
    //         .every(elem => elem !== typeof 'object');
    //       if (!hasObjects) {
    //         return value.join(', ');
    //       }
    //       return value.map(obj => {
    //         return obj.name ? obj.name : 'Unknown object';
    //       }).join(', ');
    //     }
    //     return value.name ? value.name : 'Unknown object';
    //   }
    //   // If default & not other cond, return value with no transform
    //   return value;
    // }

    /* Date */
    if (formatting.type === 'date') {
      if (formatting.formatArgs) {
        console.log('formating date  ' + value);
        return this.datePipe.transform(value, formatting.formatArgs);
      }
      return this.datePipe.transform(value, 'fullDate');
    }

    /* Currency */
    if (formatting.type === 'currency') {
      return this.currencyPipe.transform(value);
    }

    return value;
  }
}


