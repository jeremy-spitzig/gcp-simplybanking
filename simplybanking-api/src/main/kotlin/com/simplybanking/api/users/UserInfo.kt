package com.simplybanking.api.users

import java.util.*

data class UserInfo (
    val id:String,
    val firstName:String,
    val lastName:String,
    val birthDate: Date,
    val username:String
)