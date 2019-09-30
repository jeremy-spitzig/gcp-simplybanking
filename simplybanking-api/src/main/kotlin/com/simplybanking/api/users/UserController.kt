package com.simplybanking.api.users

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    val userService: UserService
) {
    @GetMapping(value=["", "/"])
    fun listUsers() = userService.listUsers()

    @PostMapping("/register")
    fun createUser(@RequestBody request:CreateUserRequest) = userService.createUser(request)
}