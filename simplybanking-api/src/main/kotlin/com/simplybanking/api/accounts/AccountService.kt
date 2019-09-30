package com.simplybanking.api.accounts

import org.springframework.stereotype.Service

@Service
class AccountService(
    val accountRepository: AccountRepository
) {
    fun listAccountsByOwnerId(ownerId:String) = accountRepository.findAccountsByOwnerId(ownerId)
    fun getAccountById(accountId:String) = accountRepository.findById(accountId)
    fun saveAccount(account:Account) = accountRepository.save(account)
}