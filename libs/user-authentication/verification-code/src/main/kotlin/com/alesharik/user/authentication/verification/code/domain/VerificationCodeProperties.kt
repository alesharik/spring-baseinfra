package com.alesharik.user.authentication.verification.code.domain

import java.time.Duration

interface VerificationCodeProperties {
    val verificationCodeSendTimeout: Duration
    val verificationCodeTries: Int
}
