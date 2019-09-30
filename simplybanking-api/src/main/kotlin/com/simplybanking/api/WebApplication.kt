package com.simplybanking.api

import com.simplybanking.api.accounts.Account
import com.simplybanking.api.accounts.AccountRepository
import com.simplybanking.api.headline.Headline
import com.simplybanking.api.headline.HeadlineRepository
import com.simplybanking.api.users.User
import com.simplybanking.api.users.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.security.crypto.password.PasswordEncoder
import java.math.BigDecimal
import java.util.*

@EnableJpaRepositories
@SpringBootApplication
class WebApplication {
	@Bean
	fun init(
		userRepository:UserRepository,
		accountRepository: AccountRepository,
		headlineRepository: HeadlineRepository,
		passwordEncoder: PasswordEncoder
	) = CommandLineRunner {
		userRepository.deleteAll()
		accountRepository.deleteAll()
		val encodedPassword = passwordEncoder.encode("password")
		val adminUser = userRepository.save(User(randomId(), "Jeremy", "Spitzig", Date(), "jspitzig", encodedPassword, "ROLE_ADMIN,ROLE_USER"))
		val regularUser = userRepository.save(User(randomId(), "Morty", "Morty", Date(), "morty", encodedPassword, "ROLE_USER"))
		val savingsAccount = accountRepository.save(Account(randomId(), regularUser, "Savings", Account.TYPE_SAVINGS, balance = BigDecimal.valueOf(1000)))
		val checkingAccount = accountRepository.save(Account(randomId(), regularUser, "Checking", Account.TYPE_CHECKING, balance = BigDecimal.valueOf(1000)))

		headlineRepository.deleteAll()
		val headline = headlineRepository.save(Headline(postedById=adminUser.id, title="New Banking Site Launched", body="Today we're pleased to announce that our new banking site has launched!", datePosted =  Date()))
	}

	private fun randomId() = UUID.randomUUID().toString()
}

fun main(args: Array<String>) {
	runApplication<WebApplication>(*args)
}
