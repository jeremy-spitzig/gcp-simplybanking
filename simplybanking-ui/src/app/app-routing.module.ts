import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoginComponent } from './login/login.component';
import { AccountsComponent } from './accounts/accounts.component';
import { TransferComponent } from './transfer/transfer.component';
import { AccountDetailsComponent } from './account-details/account-details.component';
import {HomeComponent} from "./home/home.component";
import {RegisterComponent} from "./register/register.component";

const routes: Routes = [
  { path: '',                             component: HomeComponent },
  { path: 'login',                        component: LoginComponent },
  { path: 'register',                     component: RegisterComponent},
  { path: 'accounts',                     component: AccountsComponent },
  { path: 'transfer',                     component: TransferComponent },
  { path: 'account-details/:accountId',   component: AccountDetailsComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
