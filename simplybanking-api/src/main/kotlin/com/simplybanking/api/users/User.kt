package com.simplybanking.api.users
import java.io.Serializable
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="users")
data class User (
    @Id
    val id:String,
    val firstName:String,
    val lastName:String,
    val birthDate:Date,
    val username:String,
    val encodedPassword:String,
    val roles:String
) : Serializable