package com.simplybanking.api.users

import java.util.*

data class CreateUserRequest(
    val firstName:String,
    val lastName:String,
    val birthDate: Date,
    val username:String,
    val password:String
)