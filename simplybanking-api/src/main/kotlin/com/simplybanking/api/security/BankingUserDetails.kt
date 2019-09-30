package com.simplybanking.api.security

import com.simplybanking.api.users.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class BankingUserDetails(
    val user : User
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
            user.roles
                .split(",")
                    .map { SimpleGrantedAuthority(it) }
                    .toMutableList()

    override fun isEnabled() : Boolean = true

    override fun getUsername(): String = user.username

    override fun isCredentialsNonExpired() = true

    override fun getPassword(): String = user.encodedPassword

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true
}