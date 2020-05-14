import {Component, OnInit} from '@angular/core';
import {TableSettings} from "../../system/data-table/layout.model";
import {UsersService} from "../../shared/users.service";

@Component({
  selector: 'app-user-trips',
  templateUrl: './user-trips.component.html',
  styleUrls: ['./user-trips.component.css']
})
export class UserTripsComponent implements OnInit {
  caption = 'Users';

  usersTableSettings: TableSettings[] =
    [
      {
        primaryKey: 'id',
        header: 'Id',
      },
      {
        primaryKey: 'username',
        header: 'Username',
      },
      {
        primaryKey: 'firstName',
        header: 'First Name',
      },
      {
        primaryKey: 'secondName',
        header: 'Second Name',
      },
      {
        primaryKey: 'email',
        header: 'Email',
      },
      {
        primaryKey: 'registrationDate',
        header: 'Date of registration',
        type: 'date',
        formatArgs: 'short',
      },
      {
        primaryKey: 'imageSrc',
        header: 'Image',
      },
      {
        primaryKey: 'role',
        header: 'Role',
      }
    ];
  // users;
  users = [
    {
      id: 1,
      username: 'first',
      firstName: 'user1',
      secondName: 'user1',
      email: 'user1@email',
      registrationDate: '2013-03-01T01:10:00',
      imageSrc: 'image1',
      role: 'user'
    },
    {
      id: 3,
      username: 'username3',
      firstName: 'user3',
      secondName: 'user3',
      email: 'user3@email',
      registrationDate: '2013-03-01T01:10:00',
      imageSrc: 'image3',
      role: 'user'
    },
    {
      id: 2,
      username: 'username2',
      firstName: 'user2',
      secondName: 'user2',
      email: 'user2@email',
      registrationDate: '2013-03-01T01:10:00',
      imageSrc: 'image2',
      role: 'user'
    },
    {
      id: 4,
      username: 'username4',
      firstName: 'user4',
      secondName: 'user4',
      email: 'user4@email',
      registrationDate: '2013-03-01T01:10:00',
      imageSrc: 'image4',
      role: 'user'
    },
  ];


  constructor(private userService: UsersService) {
  }

  ngOnInit() {
    this.getUsers();
  }

  getUsers() {
    // this.users = this.userService.getAllUsers().subscribe(
    //   data => {
    //     this.users = data;
    //   }, err => {
    //     this.error = err;
    //     alert(err);
    //   }
    // )
    return this.users;
  }
}
