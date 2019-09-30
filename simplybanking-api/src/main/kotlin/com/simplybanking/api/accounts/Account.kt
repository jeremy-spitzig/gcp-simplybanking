package com.simplybanking.api.accounts

import com.simplybanking.api.users.User
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name="accounts")
data class Account (
    @Id
    val id:String,
    @ManyToOne
    val owner:User,
    val label:String,
    val accountType: String,
    var balance:BigDecimal
) {
    companion object {
        val TYPE_SAVINGS = "savings"
        val TYPE_CHECKING = "checking"
    }
}


