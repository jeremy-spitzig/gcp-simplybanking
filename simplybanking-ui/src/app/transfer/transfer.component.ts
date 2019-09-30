import { Component, OnInit } from '@angular/core';
import { AccountsService } from "../accounts/accounts.service";
import { AccountInfo } from "../accounts/account-info.model";
import {TransferRequestBody} from "./transfer-request-body.model";
import {TransferService} from "./transfer.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-transfer',
  templateUrl: './transfer.component.html',
  styleUrls: ['./transfer.component.sass']
})
export class TransferComponent implements OnInit {

  availableAccounts : AccountInfo[];
  transferRequestInfo : TransferRequestBody;

  constructor(
    private accountService : AccountsService,
    private transferService : TransferService,
    private router : Router
  ) {}

  ngOnInit() {
    this.transferRequestInfo = new TransferRequestBody();
    this.accountService.listAccounts()
      .subscribe((accounts) => {
        this.availableAccounts = accounts
      })
  }

  transfer() {
    this.transferService.enqueueTransfer(this.transferRequestInfo)
      .subscribe((_) => {
        this.router.navigate(["/accounts"])
      })
  }

}
