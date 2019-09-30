package com.simplybanking.api.users

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder
) {
    fun listUsers() =
        userRepository.findAll()
            .map(this::convertToUserInfo)

    fun getUser(userId:String) =
        userRepository.getOne(userId)
            .let(this::convertToUserInfo)

    fun getUserByUsername(username:String) =
        userRepository.findByUsername(username)
            ?.let(this::convertToUserInfo)

    fun createUser(request:CreateUserRequest) =
        User(
            id= UUID.randomUUID().toString(),
            firstName=request.firstName,
            lastName=request.lastName,
            birthDate=request.birthDate,
            username=request.username,
            encodedPassword=passwordEncoder.encode(request.password),
            roles = "ROLE_USER"
        )
        .let(userRepository::save)
        .let(this::convertToUserInfo)

    fun convertToUserInfo(user:User) =
        UserInfo(
            id=user.id,
            firstName=user.firstName,
            lastName=user.lastName,
            birthDate=user.birthDate,
            username=user.username
        )
}