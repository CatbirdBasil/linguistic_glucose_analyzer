import {Component} from '@angular/core';
import {AuthService} from "../../shared/auth.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  username;

  constructor(private authService: AuthService) {
    authService.getUsername().subscribe(
      data => this.username = data
    )
  }

  isLoggedIn() {
    return this.authService.currentUserValue !== null;
  }

  logout() {
    this.authService.logout();
  }
}
