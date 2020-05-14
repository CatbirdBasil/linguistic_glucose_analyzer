import {Component, OnInit} from '@angular/core';
import {UsersService} from "../../shared/users.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../../shared/auth.service";

@Component({
  selector: 'app-user-users',
  templateUrl: './user-users.component.html',
  styleUrls: ['./user-users.component.css']
})
export class UserUsersComponent implements OnInit {


  registrationForm: FormGroup;
  submitted = false;
  error = '';
  loading = false;
  selectedVal: string = '';

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private authService: AuthService,
              private userService: UsersService) {
  }

  get f() {
    return this.registrationForm.controls;
  }

  ngOnInit() {

    this.registrationForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(15)]],
      firstName: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(40)]],
      secondName: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(40)]],
      email: ['', [Validators.required, Validators.email, Validators.maxLength(40)]],
      role: [''],
    });

  }

  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.registrationForm.invalid) {
      return;
    }

    this.loading = true;

    const registrationPayload = {
      "name": this.registrationForm.controls.firstName.value + " " + this.registrationForm.controls.secondName.value,
      "username": this.registrationForm.controls.username.value,
      "email": this.registrationForm.controls.email.value,
      "password": "123456",
      "role": this.f.role.value
    };


    console.log(registrationPayload);
    this.authService.registration(registrationPayload).subscribe(
      () => {
        this.router.navigate(['login']);
      }, err => {
        this.error = err;
        this.loading = false;
        window.setTimeout(() => {
          this.error = '';
        }, 5000);
      }
    );
  }

  selectChangeHandler(event: any) {
    //update the ui
    this.selectedVal = event.target.value;
  }
}
