import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from "../shared/auth.service";
import {ShareDataService} from "@services/share-data.service";
import {NavigationEnd, Router} from "@angular/router";

@Component({
  selector: 'app-current-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit, OnDestroy {

  indexOfTabToOpen = null;
  navigationSubscription;

  constructor(private authService: AuthService,
              private shareDataService: ShareDataService,
              private router: Router) {

    this.navigationSubscription = this.router.events.subscribe((e: any) => {
      if (e instanceof NavigationEnd) {
        this.setTabIndex();
      }
    });
  }

  ngOnInit() {
    this.setTabIndex();
    window.scrollTo(0, 0);
  }

  ngOnDestroy() {
    this.shareDataService.tabIndex = null;
    this.shareDataService.ticketId = null;
    if (this.navigationSubscription) {
      this.navigationSubscription.unsubscribe();
    }
  }

  setTabIndex() {
    this.indexOfTabToOpen = this.shareDataService.tabIndex;
  }

  get authority(): string {
    // return this.authService.getDecodedToken().authority;
    return "ROLE_USER";
  }

}
