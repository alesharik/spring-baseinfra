package com.alesharik.user.authentication.apple.exception

import org.springframework.http.HttpStatus
import org.springframework.web.ErrorResponseException
import java.net.URI

class BadTokenException : ErrorResponseException(HttpStatus.BAD_REQUEST) {
    init {
        setType(TYPE)
        setTitle("Apple token is not valid")
    }

    companion object {
        val TYPE = URI.create("https://spring.alesharik.com/docs/error/auth/apple/bad-token")
    }
}
