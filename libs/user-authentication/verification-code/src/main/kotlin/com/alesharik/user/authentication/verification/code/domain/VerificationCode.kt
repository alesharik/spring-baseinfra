package com.alesharik.user.authentication.verification.code.domain

import java.time.ZonedDateTime

data class VerificationCode(
    val code: String,
    val expires: ZonedDateTime
)
