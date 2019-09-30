import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {AccountInfo} from "./account-info.model";

@Injectable({
  providedIn: 'root'
})
export class AccountsService {

  constructor(
    private http:HttpClient
  ) { }

  listAccounts() : Observable<AccountInfo[]> {
    return this.http.get<AccountInfo[]>(environment.baseUrl + "account")
  }


}
