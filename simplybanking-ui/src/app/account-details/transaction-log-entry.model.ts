export class TransactionLogEntry {
  constructor(
    public description : string,
    public credit : number,
    public debit : number,
    public pending : boolean = false
  ) { }
}
