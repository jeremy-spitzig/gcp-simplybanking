package com.simplybanking.api.transaction

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TransactionRepository : JpaRepository<Transaction, String> {
    @Query("""
        from Transaction t
        where t.fromAccount.id = ?1 or t.toAccount.id = ?1
    """)
    fun findAccountTransactions(accountId:String) : List<Transaction>
}