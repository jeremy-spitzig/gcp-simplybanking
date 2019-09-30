package com.simplybanking.api.transaction

import com.simplybanking.api.accounts.AccountRepository
import com.simplybanking.api.global.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class TransactionService(
    val accountRepository: AccountRepository,
    val transactionRepository: TransactionRepository
) {
    fun listTransactionsForAccount(accountId:String) =
            getAccount(accountId).let { transactionRepository.findAccountTransactions(accountId) }

    fun saveTransaction(transaction:Transaction) = transactionRepository.save(transaction)

    private fun getAccount(accountId:String) =
            accountRepository.findById(accountId)
                    .orElseThrow { EntityNotFoundException("Account", accountId) }
}