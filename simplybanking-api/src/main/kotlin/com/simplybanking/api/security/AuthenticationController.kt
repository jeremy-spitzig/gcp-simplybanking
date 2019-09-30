package com.simplybanking.api.security

import com.simplybanking.api.users.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/auth")
class AuthenticationController(
    val userService: UserService
) {

    @GetMapping(value=["/success", "/currentUser"])
    fun success(principal:Principal?) =
            principal?.name?.let(userService::getUserByUsername)

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @GetMapping("/failure")
    fun failure() = mapOf(
        Pair("failure", "Unauthorized")
    )

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/logout")
    fun logoutSuccess() = null
}