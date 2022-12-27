package com.alesharik.user.authentication.verification.code.exception

import org.springframework.http.HttpStatus
import org.springframework.web.ErrorResponseException
import java.net.URI

class VerificationSentTooFastException : ErrorResponseException(HttpStatus.BAD_REQUEST) {
    init {
        setType(TYPE)
        setTitle("Verification code sent request too fast")
        setDetail("Slow down and wait before sending another request")
    }

    companion object {
        val TYPE = URI.create("https://spring.alesharik.com/docs/error/auth/verification-sent-too-fast")
    }
}
