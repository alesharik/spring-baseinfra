package com.alesharik.user.authentication.verification.code.exception

import lombok.RequiredArgsConstructor
import org.zalando.problem.Exceptional
import org.zalando.problem.Status
import org.zalando.problem.StatusType
import org.zalando.problem.ThrowableProblem
import java.net.URI
import java.time.Duration

@RequiredArgsConstructor
class VerificationCodeRequiredException(
    private val timeout: Duration
) : ThrowableProblem() {
    override fun getCause(): Exceptional? = super.cause

    override fun getType(): URI = TYPE

    override fun getTitle(): String = "Verification code required"

    override fun getDetail(): String = "The code was sent to you. You should provide it to auth handler"

    override fun getStatus(): StatusType = Status.BAD_REQUEST

    override fun getParameters(): Map<String, Any> = mapOf("timeoutSeconds" to timeout.toSeconds())

    companion object {
        val TYPE = URI.create("https://spring.alesharik.com/docs/error/auth/verification-code-required")
    }
}
