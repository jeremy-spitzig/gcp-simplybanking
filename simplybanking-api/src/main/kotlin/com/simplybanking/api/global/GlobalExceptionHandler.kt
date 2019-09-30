package com.simplybanking.api.global

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun entityNotFoundExceptionHandler(exception:EntityNotFoundException) =
            exceptionBody(exception)

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun fallbackExceptionHandler(exception:Throwable) =
            exceptionBody(exception)



    private fun exceptionBody(exception:Throwable) =
            ExceptionBody("An exception occurred: " + exception.message).apply {
        logger.error("An exception occurred", exception)
    }

    inner class ExceptionBody(
        val description: String
    ) {
        val id = System.currentTimeMillis()
    }
}