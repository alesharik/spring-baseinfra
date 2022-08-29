package com.alesharik.user.authentication.verification.code.exception

import lombok.RequiredArgsConstructor
import org.zalando.problem.Exceptional
import org.zalando.problem.Status
import org.zalando.problem.StatusType
import org.zalando.problem.ThrowableProblem
import java.net.URI

@RequiredArgsConstructor
class VerificationCodeInvalidException(
    private val codeReset: Boolean
) : ThrowableProblem() {
    override fun getCause(): Exceptional? = super.cause

    override fun getType(): URI = TYPE

    override fun getTitle(): String = "Verification code invalid"

    override fun getDetail(): String = if (codeReset)
        "You typed in wrong email verification code. Code was reset"
    else
        "You typed in wrong email verification code"

    override fun getStatus(): StatusType = Status.BAD_REQUEST

    override fun getParameters(): Map<String, Any> = mapOf("codeReset" to codeReset)

    companion object {
        val TYPE = URI.create("https://spring.alesharik.com/docs/error/auth/verification-code-invalid")
    }
}
