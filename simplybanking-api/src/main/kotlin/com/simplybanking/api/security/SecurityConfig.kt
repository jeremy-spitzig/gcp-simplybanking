package com.simplybanking.api.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebSecurity
class SecurityConfig(
    val restAuthenticationEntryPoint: RestAuthenticationEntryPoint
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder() : PasswordEncoder = BCryptPasswordEncoder()

    override fun configure(http: HttpSecurity?) {
        if(http == null) return
        http
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(restAuthenticationEntryPoint)
            .and()
            .authorizeRequests()
            .antMatchers("/headline").permitAll()
            .antMatchers("/login").permitAll()
            .antMatchers("/auth/currentUser").permitAll()
            .antMatchers("/auth/logout").permitAll()
            .antMatchers("/user/register").permitAll()
            .antMatchers("/**").authenticated()
            .and()
            .formLogin()
            .successHandler(RedirectSuccessHandler())
            .failureHandler(SimpleUrlAuthenticationFailureHandler())
            .and()
            .logout()
                .logoutSuccessUrl("/auth/logout")
            .and()
            .cors();
    }


    inner class RedirectSuccessHandler : AuthenticationSuccessHandler {
        override fun onAuthenticationSuccess(
                request: HttpServletRequest,
                response: HttpServletResponse,
                authentication: Authentication) {
            response.sendRedirect("/auth/success")
        }
    }

    inner class Redirect
}
