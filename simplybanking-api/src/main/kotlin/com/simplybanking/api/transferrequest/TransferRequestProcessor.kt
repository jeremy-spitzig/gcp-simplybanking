package com.simplybanking.api.transferrequest

import com.simplybanking.api.accounts.AccountService
import com.simplybanking.api.transaction.Transaction
import com.simplybanking.api.transaction.TransactionService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.*


@Component
class TransferRequestProcessor(
    val transferRequestService: TransferRequestService,
    val accountService: AccountService,
    val transactionService: TransactionService
) {

    @Scheduled(fixedRate = 60000)
    fun processPendingTransferRequests() {
        val pendingRequests = transferRequestService.listPendingTransferRequests()
        pendingRequests
                .filter { request -> request.fromAccount.balance > request.amount }
                .forEach { request ->
                    val fromAccount = accountService.getAccountById(request.fromAccount.id)
                    val toAccount = accountService.getAccountById(request.toAccount.id)
                    if(fromAccount.isPresent && toAccount.isPresent) {
                        toAccount.get().balance = toAccount.get().balance + request.amount
                        fromAccount.get().balance = fromAccount.get().balance - request.amount
                        accountService.saveAccount(toAccount.get())
                        accountService.saveAccount(fromAccount.get())
                        val transaction = Transaction(
                            UUID.randomUUID().toString(),
                        "Transfer from account \"${fromAccount.get().label}\" to account \"${toAccount.get().label}\"",
                            fromAccount.get(),
                            toAccount.get(),
                            request.amount
                        )
                        transactionService.saveTransaction(transaction)
                        transferRequestService.completeTransferRequest(request)
                    }
                }
    }
}