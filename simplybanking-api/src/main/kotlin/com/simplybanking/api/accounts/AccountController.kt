package com.simplybanking.api.accounts

import com.simplybanking.api.users.UserService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/account")
class AccountController(
    val accountService: AccountService,
    val userService: UserService
) {
    @GetMapping(value=["", "/"])
    fun listAccounts(pricipal:Principal) =
            (pricipal?.name
                    ?.let(userService::getUserByUsername)
                    ?: throw UsernameNotFoundException("User not found"))
                    .let { it.id }
                    .let(accountService::listAccountsByOwnerId)

}