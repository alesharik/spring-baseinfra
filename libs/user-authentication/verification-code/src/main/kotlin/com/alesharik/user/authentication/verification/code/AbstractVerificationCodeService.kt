package com.alesharik.user.authentication.verification.code

import com.alesharik.user.authentication.verification.code.domain.VerificationCode
import com.alesharik.user.authentication.verification.code.domain.VerificationCodeEntityBase
import com.alesharik.user.authentication.verification.code.domain.VerificationCodeProperties
import com.alesharik.user.authentication.verification.code.exception.VerificationCodeExpiredException
import com.alesharik.user.authentication.verification.code.exception.VerificationCodeInvalidException
import com.alesharik.user.authentication.verification.code.exception.VerificationCodeNotRequestedException
import com.alesharik.user.authentication.verification.code.exception.VerificationCodeRequiredException
import com.alesharik.user.authentication.verification.code.exception.VerificationSentTooFastException
import java.time.ZonedDateTime

abstract class AbstractVerificationCodeService<T: VerificationCodeEntityBase, R>(
    private val properties: VerificationCodeProperties,
    private val verificationCodeGenerator: VerificationCodeGenerator
) {
    protected abstract fun saveAuth(auth: T)

    protected abstract fun sendCode(code: VerificationCode, to: R)

    open fun ensureVerificationSuccessful(
        code: String?,
        auth: T,
        recipientInfo: R
    ) {
        if (code == null) { // no code for verification? then send it and return error
            sendCode(auth, recipientInfo)
            saveAuth(auth)
            throw VerificationCodeRequiredException(properties.verificationCodeSendTimeout)
        }

        val verificationCodeExpires = auth.verificationCodeExpires
        if (verificationCodeExpires == null || auth.verificationCode == null) // no verification code was send at all?
            throw VerificationCodeNotRequestedException()
        if (verificationCodeExpires.isBefore(ZonedDateTime.now())) // code expired? Then send error
            throw VerificationCodeExpiredException()
        if (code != auth.verificationCode) {
            // codes mismatch? Then send error
            auth.tries += 1
            if (auth.tries > properties.verificationCodeTries) {
                auth.verificationCode = null
                auth.verificationCodeExpires = null
                auth.tries = 0
                saveAuth(auth)
                throw VerificationCodeInvalidException(true)
            } else {
                saveAuth(auth)
                throw VerificationCodeInvalidException(false)
            }
        }
        // code is valid and not expired? Auth successful
        auth.verified = true
        saveAuth(auth)
    }

    protected open fun generateCode(recipient: R): VerificationCode {
        return verificationCodeGenerator.generate()
    }

    open fun sendCode(auth: T, recipientInfo: R) {
        val lastSentTime = auth.lastSentTime
        if (lastSentTime != null && lastSentTime.plus(properties.verificationCodeSendTimeout)
                .isAfter(ZonedDateTime.now())
        )
            throw VerificationSentTooFastException()
        val code = generateCode(recipientInfo)
        auth.verificationCode = code.code
        auth.verificationCodeExpires = code.expires
        auth.lastSentTime = ZonedDateTime.now()
        auth.tries = 0
        sendCode(code, recipientInfo)
    }
}
