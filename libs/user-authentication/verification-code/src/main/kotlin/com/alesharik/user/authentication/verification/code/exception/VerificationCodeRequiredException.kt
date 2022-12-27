package com.alesharik.user.authentication.verification.code.exception

import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.web.ErrorResponseException
import java.net.URI
import java.time.Duration

@RequiredArgsConstructor
class VerificationCodeRequiredException(timeout: Duration) : ErrorResponseException(HttpStatus.BAD_REQUEST) {
    init {
        setType(TYPE)
        setTitle("Verification code required")
        setDetail("The code was sent to you. You should provide it to auth handler")
        body.setProperty("timeoutSeconds", timeout.toSeconds())
    }

    companion object {
        val TYPE = URI.create("https://spring.alesharik.com/docs/error/auth/verification-code-required")
    }
}
