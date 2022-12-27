package com.alesharik.user.authentication.verification.code.exception

import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.web.ErrorResponseException
import java.net.URI

@RequiredArgsConstructor
class VerificationCodeInvalidException(codeReset: Boolean) : ErrorResponseException(HttpStatus.BAD_REQUEST) {
    init {
        setType(TYPE)
        setTitle("Verification code invalid")
        setDetail(
            if (codeReset)
                "You typed in wrong email verification code. Code was reset"
            else
                "You typed in wrong email verification code"
        )
        body.setProperty("codeReset", codeReset)
    }

    companion object {
        val TYPE = URI.create("https://spring.alesharik.com/docs/error/auth/verification-code-invalid")
    }
}
