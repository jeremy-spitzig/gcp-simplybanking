package com.simplybanking.api.headline

import org.springframework.data.annotation.Id
import java.util.*

data class Headline (
    @Id
    val id : Long? = null,
    val postedById : String,
    val title : String,
    val body : String,
    val datePosted : Date
)