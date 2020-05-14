import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-user-summary',
  templateUrl: './user-summary.component.html',
  styleUrls: ['./user-summary.component.css']
})
export class UserSummaryComponent implements OnInit {
  public currentUser: any;

  constructor(private router: Router) {
  }

  ngOnInit() {
  }
  editUser() {
    this.router.navigate(['account/edit']);
  }

  deleteUser() {
    console.log("deleted");
    this.router.navigate(['account/delete']);
  }
}
