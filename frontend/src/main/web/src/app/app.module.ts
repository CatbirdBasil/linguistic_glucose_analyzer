import {BrowserModule, Title} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HeaderComponent} from './system/header/header.component';
import {LoginComponent} from './auth/login/login.component';
import {RegistrationComponent} from './auth/registration/registration.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {IndexComponent} from './system/index/index.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";

import {EditUserComponent} from './user/edit-user/edit-user.component';
import {PasswordRecoveryComponent} from './auth/password-recovery/password-recovery.component';
import {routing} from "./app.routing";
import {UserComponent} from './user/user.component';
import {JwtInterceptor} from "./helpers/jwt.interceptor";
import {HttpErrorInterceptor} from "./helpers/HttpError.interceptor";
import {UserSummaryComponent} from './user/user-summary/user-summary.component';
import {UserUsersComponent} from './user/user-users/user-users.component';
import {UserBundlesComponent} from './user/user-bundles/user-bundles.component';
import {UserTripsComponent} from './user/user-trips/user-trips.component';
import {UserServicesComponent} from './user/user-services/user-services.component';
import {UserDiscountsComponent} from './user/user-discounts/user-discounts.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {DashboardComponent} from './user/dashboard/dashboard.component';
import {MatButtonModule, MatCardModule, MatGridListModule, MatIconModule, MatMenuModule} from '@angular/material';
import {LayoutModule} from '@angular/cdk/layout';

import {MDBBootstrapModule} from 'angular-bootstrap-md';

import {ToTitlePipe} from "./system/data-table/toTitle.pipe";
import {FormatCellPipe} from "./system/data-table/formatCell.pipe";
import {DataTableComponent} from "./system/data-table/data-table.component";
import {RegistrationSuccessfulComponent} from "./auth/registration/registration-successful/registration-successful.component";
import {MaterialModule} from "./helpers/material.module";
import {CurrencyPipe, DatePipe} from "@angular/common";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    RegistrationComponent,
    IndexComponent,
    UserComponent,
    EditUserComponent,
    PasswordRecoveryComponent,
    UserSummaryComponent,
    UserUsersComponent,
    UserBundlesComponent,
    UserTripsComponent,
    UserServicesComponent,
    UserDiscountsComponent,
    DashboardComponent,
    RegistrationSuccessfulComponent,
    DataTableComponent,
    FormatCellPipe,
    ToTitlePipe,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    routing,
    BrowserAnimationsModule,
    MatGridListModule,
    MatCardModule,
    MatMenuModule,
    MatIconModule,
    MatButtonModule,
    LayoutModule,
    MDBBootstrapModule,
    MDBBootstrapModule,
    MaterialModule,
  ],
  providers: [
    Title,
    CurrencyPipe,
    DatePipe,
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true},
  ],
  bootstrap: [AppComponent],
  entryComponents: []
})
export class AppModule {
}
