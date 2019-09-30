package com.simplybanking.api.transferrequest

import com.simplybanking.api.accounts.AccountService
import com.simplybanking.api.global.EntityNotFoundException
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/transfer")
class TransferRequestController(
    val accountService: AccountService,
    val transferRequestService: TransferRequestService
) {
    @GetMapping(value=["", "/"])
    fun listTransferRequests(
        @RequestParam accountId : String,
        @RequestParam(required = false, defaultValue = "true") onlyPending:Boolean
    ) = if(onlyPending) transferRequestService.listPendingTransferRequestsForAccount(accountId)
        else transferRequestService.listAllTransferRequestsForAccount(accountId)

    @PostMapping(value=["", "/"])
    fun requestTransfer(@RequestBody request:TransferRequestBody) {
        val fromAccount = accountService.getAccountById(request.fromAccountId)
        val toAccount = accountService.getAccountById(request.toAccountId)
        if(!fromAccount.isPresent) {
            throw EntityNotFoundException("Account", request.fromAccountId)
        }
        if(!toAccount.isPresent) {
            throw EntityNotFoundException("Account", request.toAccountId)
        }
        transferRequestService.enqueueTransfer(TransferRequest(
                UUID.randomUUID().toString(),
                fromAccount.get(),
                toAccount.get(),
                request.amount,
                false))
    }
}