package com.simplybanking.api.transaction

import com.simplybanking.api.accounts.Account
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name="transactions")
data class Transaction (
    @Id
    val id:String,
    val description:String,
    @ManyToOne
    val fromAccount:Account,
    @ManyToOne
    val toAccount:Account,
    val amount:BigDecimal
)