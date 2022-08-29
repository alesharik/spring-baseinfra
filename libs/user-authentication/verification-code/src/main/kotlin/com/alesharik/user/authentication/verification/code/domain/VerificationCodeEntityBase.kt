package com.alesharik.user.authentication.verification.code.domain

import java.time.ZonedDateTime

interface VerificationCodeEntityBase {
    var lastSentTime: ZonedDateTime?
    var tries: Int
    var verificationCode: String?
    var verificationCodeExpires: ZonedDateTime?
    var verified: Boolean
}
