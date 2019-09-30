package com.simplybanking.api.accounts

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AccountRepository : JpaRepository<Account, String> {
    @Query("""
        from Account a
        where a.owner.id = ?1
    """)
    fun findAccountsByOwnerId(ownerId:String) : List<Account>
}