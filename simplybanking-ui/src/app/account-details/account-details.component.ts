import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {TransactionLogService} from "./transaction-log.service";
import {TransactionLogEntry} from "./transaction-log-entry.model";
import {TransferService} from "../transfer/transfer.service";
import {map} from "rxjs/operators";
import {TransferRequestBody} from "../transfer/transfer-request-body.model";
import {TransferRequest} from "../transfer/transfer-request.model";
import {combineLatest} from "rxjs";

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.sass']
})
export class AccountDetailsComponent implements OnInit {

  logEntries : TransactionLogEntry[];

  constructor(
    private route:ActivatedRoute,
    private transactionLogService:TransactionLogService,
    private transferService:TransferService
  ) { }

  ngOnInit() {
    const accountId = this.route.snapshot.paramMap.get("accountId");

    const pendingTransactions = map((transferRequests:TransferRequest[], index) => {
      return transferRequests.map((transferRequest) => {
        let credit = transferRequest.toAccount.id == accountId ? transferRequest.amount : null;
        let debit = transferRequest.fromAccount.id == accountId ? transferRequest.amount : null;
        return new TransactionLogEntry(
          `Pending: Transfer from account "${transferRequest.fromAccount.label}" to account "${transferRequest.toAccount.label}"`,
          credit,
          debit,
          true
        )
      });
    })(this.transferService.listTransferRequestsForAccount(accountId, true));

    map((logEntryList : [TransactionLogEntry[], TransactionLogEntry[]], index) => {
      return logEntryList[0].concat(logEntryList[1]);
    })(
      combineLatest(
        pendingTransactions,
        this.transactionLogService.loadTransactionLog(accountId)
      )
    ).subscribe((logEntries) => this.logEntries = logEntries);;


  }

}
