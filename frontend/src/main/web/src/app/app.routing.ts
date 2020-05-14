import {RouterModule, Routes} from '@angular/router';
import {EditUserComponent} from "./user/edit-user/edit-user.component";
import {RegistrationComponent} from "./auth/registration/registration.component";
import {IndexComponent} from "./system/index/index.component";
import {AuthGuard} from "./auth/auth.guard";
import {PasswordRecoveryComponent} from "./auth/password-recovery/password-recovery.component";
import {LoginComponent} from "./auth/login/login.component";
import {UserComponent} from "./user/user.component";

const appRoutes: Routes = [
  {path: 'registration', component: RegistrationComponent},
  {path: 'passwordRecovery', component: PasswordRecoveryComponent},
  {path: 'account', component: UserComponent, canActivate: [AuthGuard]},
  {path: 'account/edit', component: EditUserComponent, canActivate: [AuthGuard]},
  {path: 'login', component: LoginComponent},
  {path: '', component: IndexComponent},
  {path: '**', redirectTo: ''},
];

export const routing = RouterModule.forRoot(appRoutes, {useHash: true});
