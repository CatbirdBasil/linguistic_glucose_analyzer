import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MustMatch} from "../../helpers/must-mutch.validator";

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {
  editUserForm: FormGroup;
  changePasswordForm: FormGroup;
  submittedEditing = false;
  submittedPasswordChanging = false;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.editUserForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(15)]],
      firstName: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(40)]],
      secondName: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(40)]],
      email: ['', [Validators.required, Validators.email, Validators.maxLength(40)]],
    });
    this.changePasswordForm = this.formBuilder.group({
      oldPassword: ['', [Validators.required]],
      newPassword: ['', [Validators.required]],
      newPasswordConfirm: ['', [Validators.required]],
    }, {
      validator: MustMatch('newPassword', 'newPasswordConfirm')
    });
  }

  get userDataForm() { return this.editUserForm.controls; }
  get passwordForm() { return this.changePasswordForm.controls; }

  updateUser() {
    this.submittedEditing = true;
  }

  changePassword() {
    this.submittedPasswordChanging = true;
  }
}
