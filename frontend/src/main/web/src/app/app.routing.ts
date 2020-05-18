import {RouterModule, Routes} from '@angular/router';
import {RegistrationComponent} from "./auth/registration/registration.component";
import {AuthGuard} from "./auth/auth.guard";
import {LoginComponent} from "./auth/login/login.component";
import {UserComponent} from "./user/user.component";
import {RegistrationSuccessfulComponent} from "./auth/registration/registration-successful/registration-successful.component";
import {IndexComponent} from "./system/index/index.component";

const appRoutes: Routes = [
  {path: 'registration', component: RegistrationComponent},
  {path: 'registrationSuccessful', component: RegistrationSuccessfulComponent},
  {
    path: 'account',
    component: UserComponent,
    canActivate: [AuthGuard],
    runGuardsAndResolvers: 'always'
  },
  {path: 'login', component: LoginComponent},
  {path: '', component: IndexComponent},
  {path: '**', redirectTo: ''},
];

export const routing = RouterModule.forRoot(appRoutes, {useHash: true, onSameUrlNavigation: "reload"});
