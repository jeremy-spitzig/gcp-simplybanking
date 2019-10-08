package com.simplybanking.api.global

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.session.data.redis.config.ConfigureRedisAction

@Configuration
class SessionConfiguration {
    @Bean
    fun configureRedisAction() = ConfigureRedisAction.NO_OP
}