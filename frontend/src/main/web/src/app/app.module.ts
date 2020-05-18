import {BrowserModule, Title} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HeaderComponent} from './system/header/header.component';
import {LoginComponent} from './auth/login/login.component';
import {RegistrationComponent} from './auth/registration/registration.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {IndexComponent} from './system/index/index.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";

import {routing} from "./app.routing";
import {UserComponent} from './user/user.component';
import {JwtInterceptor} from "@helpers/jwt.interceptor";
import {HttpErrorInterceptor} from "@helpers/HttpError.interceptor";
import {AngularMultiSelectModule} from 'angular2-multiselect-dropdown';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {LayoutModule} from '@angular/cdk/layout';

import {MDBBootstrapModule} from 'angular-bootstrap-md';

import {RegistrationSuccessfulComponent} from "./auth/registration/registration-successful/registration-successful.component";
import {MaterialModule} from "@helpers/material.module";
import {CurrencyPipe, DatePipe} from "@angular/common";

import {AgmCoreModule} from '@agm/core';
import {NgSelectModule} from '@ng-select/ng-select';
import {ShareDataService} from "@services/share-data.service";
import {SnackbarComponent} from './model/snackbar/snackbar.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { GlucoseRecordsComponent } from './glucose-records/glucose-records.component';
import {JwPaginationComponent} from "jw-angular-pagination";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    RegistrationComponent,
    IndexComponent,
    UserComponent,
    RegistrationSuccessfulComponent,
    SnackbarComponent,
    GlucoseRecordsComponent,
    JwPaginationComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    routing,
    BrowserAnimationsModule,
    LayoutModule,
    MDBBootstrapModule.forRoot(),
    MaterialModule,
    AngularMultiSelectModule,
    NgbModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyAcaaElwdGQIGIRnr2Ay1Hl9BuqEwDpjeY'
    }),
    NgbModule,
    NgSelectModule
  ],
  providers: [
    Title,
    CurrencyPipe,
    DatePipe,
    ShareDataService,
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true},
  ],
  bootstrap: [AppComponent],
  entryComponents: [SnackbarComponent]
})
export class AppModule {
}

