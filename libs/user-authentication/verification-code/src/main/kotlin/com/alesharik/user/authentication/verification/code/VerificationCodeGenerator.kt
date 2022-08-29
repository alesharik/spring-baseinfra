package com.alesharik.user.authentication.verification.code

import com.alesharik.user.authentication.verification.code.domain.VerificationCode
import java.security.SecureRandom
import java.time.Duration
import java.time.ZonedDateTime
import kotlin.math.pow

open class VerificationCodeGenerator(
    private val size: Int,
    private val expiresIn: Duration
) {
    private val random = SecureRandom()

    open fun generate(): VerificationCode {
        val code = "%0${size}d".format(random.nextInt(10.0.pow(size).toInt()))
        return VerificationCode(
            code,
            ZonedDateTime.now().plus(expiresIn)
        )
    }
}
