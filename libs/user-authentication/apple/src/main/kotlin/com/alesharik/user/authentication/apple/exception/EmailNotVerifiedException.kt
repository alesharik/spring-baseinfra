package com.alesharik.user.authentication.apple.exception

import org.springframework.http.HttpStatus
import org.springframework.web.ErrorResponseException
import java.net.URI

class EmailNotVerifiedException : ErrorResponseException(HttpStatus.BAD_REQUEST) {
    init {
        setType(TYPE)
        setTitle("Email not verified")
        setDetail("Please, verify your email in Apple account")
    }

    companion object {
        val TYPE = URI.create("https://spring.alesharik.com/docs/error/auth/apple/email-not-verified")
    }
}
