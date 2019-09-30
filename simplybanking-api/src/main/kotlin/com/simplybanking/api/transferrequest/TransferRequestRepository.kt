package com.simplybanking.api.transferrequest

import org.springframework.data.jpa.repository.JpaRepository

interface TransferRequestRepository : JpaRepository<TransferRequest, String> {
    fun findAllByFromAccountId(fromAccountId: String) : List<TransferRequest>
    fun findAllByToAccountId(toAccountId: String) : List<TransferRequest>
    fun findAllByFromAccountIdAndProcessedFalse(fromAccountId:String) : List<TransferRequest>
    fun findAllByToAccountIdAndProcessedFalse(toAccountId:String) : List<TransferRequest>
    fun findAllByProcessedFalse() : List<TransferRequest>
}