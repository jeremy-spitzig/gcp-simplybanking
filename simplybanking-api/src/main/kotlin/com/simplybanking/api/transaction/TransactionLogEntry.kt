package com.simplybanking.api.transaction

import java.math.BigDecimal

data class TransactionLogEntry(
    val description : String,
    val credit : BigDecimal? = null,
    val debit : BigDecimal? = null
)