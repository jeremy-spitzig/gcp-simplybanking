package com.simplybanking.api.security

import com.simplybanking.api.users.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class BankingUserDetailsService(
    val userRepository : UserRepository
) : UserDetailsService  {

    override fun loadUserByUsername(username: String?): UserDetails =
            username?.let(userRepository::findByUsername)
                    ?.let { BankingUserDetails(it) }
                    ?: throw UsernameNotFoundException("User not found")

}