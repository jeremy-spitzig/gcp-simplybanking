package com.simplybanking.api.transaction

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/transaction")
class TransactionController(
    val transactionService: TransactionService
) {

    @GetMapping("/log/{accountId}")
    fun listTransactionsForAccount(@PathVariable accountId:String) =
        transactionService.listTransactionsForAccount(accountId)
            .map {
                TransactionLogEntry(
                    it.description,
                    if(it.toAccount.id == accountId) it.amount else null,
                    if(it.fromAccount.id == accountId) it.amount else null
                )
            }
}