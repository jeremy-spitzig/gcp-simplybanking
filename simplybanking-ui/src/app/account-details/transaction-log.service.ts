import { Injectable } from '@angular/core';
import {TransactionLogEntry} from "./transaction-log-entry.model";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class TransactionLogService {

  constructor(
    private http:HttpClient
  ) { }

  loadTransactionLog(accountId:string) : Observable<TransactionLogEntry[]> {
    return this.http.get<[TransactionLogEntry]>( `${environment.baseUrl}transaction/log/${accountId}`)
  }
}
