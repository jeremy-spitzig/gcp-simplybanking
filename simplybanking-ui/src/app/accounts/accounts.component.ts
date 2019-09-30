import { Component, OnInit } from '@angular/core';
import {AccountsService} from "./accounts.service";
import {AccountInfo} from "./account-info.model";

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.sass']
})
export class AccountsComponent implements OnInit {

  accounts:AccountInfo[];

  constructor(
    private accountsService:AccountsService
  ) {

  }

  ngOnInit() {
    this.accountsService.listAccounts()
      .subscribe((accounts)=> {
        this.accounts = accounts
      })
  }

}
