import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-registration-successful',
  templateUrl: './registration-successful.component.html',
  styleUrls: ['./registration-successful.component.css']
})

export class RegistrationSuccessfulComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit() {
    window.setTimeout(() => {
      this.router.navigate(['login']);
    }, 5000);
  }

}
