import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "@services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-password-recovery',
  templateUrl: './password-recovery.component.html',
  styleUrls: ['./password-recovery.component.scss']
})
export class PasswordRecoveryComponent implements OnInit {
  recoveryPasswordForm: FormGroup;
  submitted = false;
  error = '';
  loading = false;

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private authService: AuthService) {

  }

  ngOnInit() {
    this.recoveryPasswordForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  get f() { return this.recoveryPasswordForm.controls; }

  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.recoveryPasswordForm.invalid) {
      return;
    }

    this.loading = true;

    // this.authService.recoveryPassword(this.recoveryPasswordForm.controls.email.value).subscribe(
    //   () => {
    //     this.router.navigate(['login']);
    //   }, err => {
    //     this.error = err;
    //     this.loading = false;
    //     window.setTimeout(() => {
    //       this.error = '';
    //     }, 5000);
    //   }
    // );
  }
}
