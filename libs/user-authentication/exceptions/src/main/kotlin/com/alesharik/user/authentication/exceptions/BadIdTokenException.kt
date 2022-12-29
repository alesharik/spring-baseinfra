package com.alesharik.user.authentication.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.ErrorResponseException
import java.net.URI
import java.util.*

class BadIdTokenException(system: String) : ErrorResponseException(HttpStatus.BAD_REQUEST) {
    init {
        setType(TYPE)
        val systemFormatted =
            system.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        setTitle("$systemFormatted token is not valid")
        body.setProperty("system", system)
    }

    companion object {
        val TYPE = URI.create("https://spring.alesharik.com/docs/error/auth/apple/bad-id-token")
    }
}
