package com.simplybanking.api.transferrequest

import com.simplybanking.api.accounts.AccountRepository
import com.simplybanking.api.global.EntityNotFoundException
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class TransferRequestService(
    val transferRequestRepository: TransferRequestRepository,
    val accountRepository: AccountRepository
) {
    fun enqueueTransfer(transferRequest: TransferRequest) {
        val fromAccount = getAccount(transferRequest.fromAccount.id)
        if(fromAccount.balance < transferRequest.amount) {
            throw InsufficientBalanceException(transferRequest.fromAccount.id, transferRequest.amount, "transfer")
        }
        transferRequestRepository.save(transferRequest)
    }

    fun listPendingTransferRequests() = transferRequestRepository.findAllByProcessedFalse()

    fun listPendingTransferRequestsForAccount(accountId:String) =
            transferRequestRepository.findAllByFromAccountIdAndProcessedFalse(accountId) +
            transferRequestRepository.findAllByToAccountIdAndProcessedFalse(accountId)

    fun listAllTransferRequestsForAccount(accountId:String) =
            transferRequestRepository.findAllByFromAccountId(accountId) +
            transferRequestRepository.findAllByToAccountId(accountId)


    fun completeTransferRequest(transferRequest: TransferRequest) {
        transferRequest.processed = true
        transferRequestRepository.save(transferRequest)
    }

    private fun getAccount(accountId:String) =
            accountRepository.findById(accountId)
                    .orElseThrow { EntityNotFoundException("Account", accountId) }


    internal class InsufficientBalanceException (
        accountId : String,
        requestedAmount : BigDecimal,
        requestedAction : String
    ) : Exception("Insufficient balance in account \"${accountId}\" to ${requestedAction} ${requestedAmount}")

}