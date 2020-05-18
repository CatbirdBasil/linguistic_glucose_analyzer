import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ShareDataService {
  public tabIndex: number;
  // public serviceId: number;
  public ticketId: number;

  public snackBarMessage: string;

  public dialog: string;
}
