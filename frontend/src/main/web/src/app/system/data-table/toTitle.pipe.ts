import {Pipe, PipeTransform} from '@angular/core';
import {TitleCasePipe} from '@angular/common';

@Pipe({name: 'toTitle'})
export class ToTitlePipe implements PipeTransform {


  constructor() {
  }

  transform(value: any): string {
    if (value === undefined) {
      return 'not available';
    }
    let defaultTitlePipe = new TitleCasePipe();
    return defaultTitlePipe.transform(value.replace(/([^A-Z])([A-Z])/g, '$1 $2'));
  }

}

