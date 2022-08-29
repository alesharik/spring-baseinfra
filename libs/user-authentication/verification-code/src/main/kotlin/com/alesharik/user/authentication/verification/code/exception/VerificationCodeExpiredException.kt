package com.alesharik.user.authentication.verification.code.exception

import org.zalando.problem.Exceptional
import org.zalando.problem.Status
import org.zalando.problem.StatusType
import org.zalando.problem.ThrowableProblem
import java.net.URI

class VerificationCodeExpiredException : ThrowableProblem() {
    override fun getCause(): Exceptional? = super.cause

    override fun getType(): URI = TYPE

    override fun getTitle(): String = "Verification code expired"

    override fun getDetail(): String = "You should send another verification code request"

    override fun getStatus(): StatusType = Status.BAD_REQUEST

    companion object {
        val TYPE = URI.create("https://spring.alesharik.com/docs/error/auth/verification-code-expired")
    }
}
