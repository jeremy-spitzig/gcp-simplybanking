import { Injectable } from '@angular/core';
import {TransferRequestBody} from "./transfer-request-body.model";
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {TransferRequest} from "./transfer-request.model";

@Injectable({
  providedIn: 'root'
})
export class TransferService {

  constructor(
    private http:HttpClient
  ) { }

  enqueueTransfer(transferRequestInfo:TransferRequestBody) : Observable<any> {
    return this.http.post(environment.baseUrl + "transfer", transferRequestInfo)
  }

  listTransferRequestsForAccount(accountId:string, onlyPending:Boolean) : Observable<TransferRequest[]> {
    return this.http.get<[TransferRequest]>(environment.baseUrl + "transfer", {
      params: {
        accountId:accountId,
        onlyPending:"" + onlyPending
      }
    });
  }
}
