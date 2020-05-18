import {Component} from '@angular/core';
import {ShareDataService} from "@services/share-data.service";

@Component({
  selector: 'app-snackbar',
  templateUrl: './snackbar.component.html',
  styleUrls: ['./snackbar.component.scss']
})
export class SnackbarComponent {

  constructor(public shareService: ShareDataService) {
  }
}
