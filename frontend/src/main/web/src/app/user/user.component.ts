import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from "../shared/auth.service";
import {ShareDataService} from "@services/share-data.service";
import {NavigationEnd, Router} from "@angular/router";
import {UserSummary} from "@models/user-summary";
import {UserService} from "../service/user-service";

@Component({
  selector: 'app-current-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit, OnDestroy {

  public isEditing;
  public isSaving;
  public editableUser: UserSummary;

  public currentUser: UserSummary;
  indexOfTabToOpen = null;
  navigationSubscription;
  selectedValue = {id: 2, name: 'Second type'};
  diabetesType = [
    {id: 1, name: "First type"},
    {id: 2, name: "Second type"},
    {id: 3, name: "No diabetes"}
  ];

  constructor(private authService: AuthService,
              private shareDataService: ShareDataService,
              private router: Router,
              private usersService: UserService) {

    this.navigationSubscription = this.router.events.subscribe((e: any) => {
      if (e instanceof NavigationEnd) {
        this.setTabIndex();
      }
    });
  }

  ngOnInit() {
    // this.isEditing = true;
    this.isEditing = false;
    this.isSaving = false;

    this.usersService.getCurrentUserData().subscribe(data => {
      this.currentUser = data;
    }, err => {
      alert(err);
    });
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

  cancel() {
    console.log(this.editableUser);
  }

  clicked() {
    console.log(this.currentUser)
    this.editableUser = new class implements UserSummary {
      email: String;
      username: String;
      name: number;
      surname: number;
      birthDate: number;
      diabetesTypeId: number;
    }
    this.editableUser.username = this.currentUser.username;
    this.editableUser.email = this.currentUser.email;
    this.editableUser.name = this.currentUser.name;
    this.editableUser.surname = this.currentUser.surname;
    this.editableUser.email = this.currentUser.email;
    this.editableUser.birthDate = this.currentUser.birthDate;
    this.editableUser.diabetesTypeId = this.currentUser.diabetesTypeId;

    if (this.selectedValue.id != 1 && this.selectedValue.id != 3) {
      this.selectedValue.id = 2;
    }

    this.isEditing = !this.isEditing;
  }

  save() {

    // tut eto obernut v vizov becka
    this.currentUser.username = this.editableUser.username;
    this.currentUser.email = this.editableUser.email;
    this.currentUser.name = this.editableUser.name;
    this.currentUser.surname = this.editableUser.surname;
    this.currentUser.birthDate = this.editableUser.birthDate;
    this.currentUser.diabetesTypeId = this.selectedValue.id;

    this.usersService.updateCurrentUserData(this.currentUser).subscribe(
      data => {
        console.log('SAVED', this.currentUser);
        this.isSaving = false;
        this.isEditing = !this.isEditing;
      },
      error => {
        console.log(error);
        this.isSaving = false;
      }
    )
  }
}
