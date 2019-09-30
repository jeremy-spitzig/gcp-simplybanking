package com.simplybanking.api.transferrequest

import com.simplybanking.api.accounts.Account
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name="transfer_requests")
data class TransferRequest (
    @Id
    val id : String,
    @ManyToOne
    val fromAccount:Account,
    @ManyToOne
    val toAccount:Account,
    val amount:BigDecimal,
    var processed:Boolean
)