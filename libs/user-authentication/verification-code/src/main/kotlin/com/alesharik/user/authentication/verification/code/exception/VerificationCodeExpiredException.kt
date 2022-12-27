package com.alesharik.user.authentication.verification.code.exception

import org.springframework.http.HttpStatus
import org.springframework.web.ErrorResponseException
import java.net.URI

class VerificationCodeExpiredException : ErrorResponseException(HttpStatus.BAD_REQUEST) {
    init {
        setType(TYPE)
        setTitle("Verification code expired")
        setDetail("You should send another verification code request")
    }

    companion object {
        val TYPE = URI.create("https://spring.alesharik.com/docs/error/auth/verification-code-expired")
    }
}
