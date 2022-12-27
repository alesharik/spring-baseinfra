package com.alesharik.user.authentication.verification.code.exception

import org.springframework.http.HttpStatus
import org.springframework.web.ErrorResponseException
import java.net.URI

class VerificationCodeNotRequestedException : ErrorResponseException(HttpStatus.BAD_REQUEST) {
    init {
        setType(TYPE)
        setTitle("Verification code not requested")
        setDetail("You should request verification code from your auth handler")
    }

    companion object {
        val TYPE = URI.create("https://spring.alesharik.com/docs/error/auth/verification-code-not-requested")
    }
}
