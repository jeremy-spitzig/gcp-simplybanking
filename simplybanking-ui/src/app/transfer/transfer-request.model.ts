import {AccountInfo} from "../accounts/account-info.model";

export class TransferRequest {
  id : string;
  fromAccount:AccountInfo;
  toAccount:AccountInfo;
  amount:number;
  processed:boolean;
}
