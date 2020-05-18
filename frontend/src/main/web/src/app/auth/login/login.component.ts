import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "@services/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {first} from "rxjs/operators";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  submitted = false;
  returnUrl: string;
  loading = false;
  error = '';

  constructor(private formBuilder: FormBuilder,
              private authService: AuthService,
              private route: ActivatedRoute,
              private router: Router) {

  }

  ngOnInit() {
    if (this.authService.currentUserValue) {
      this.router.navigate(['']);
    }
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],
    });


    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  get f() {
    return this.loginForm.controls;
  }

  onSubmit() {
    this.submitted = true;

    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;

    const loginPayload = {
      "usernameOrEmail": this.f.username.value,
      "password": this.f.password.value
    };

    this.authService.login(loginPayload).pipe(first()).subscribe(
      () => {
        this.router.navigate(['']);
      }, err => {
        this.error = err;
        this.loading = false;
        window.setTimeout(() => {
          this.error = '';
        }, 5000);
      }
    )
  }
}


