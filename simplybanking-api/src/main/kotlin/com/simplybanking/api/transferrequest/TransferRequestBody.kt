package com.simplybanking.api.transferrequest

import com.simplybanking.api.accounts.Account
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

data class TransferRequestBody (
    val fromAccountId:String,
    val toAccountId:String,
    val amount:BigDecimal
)