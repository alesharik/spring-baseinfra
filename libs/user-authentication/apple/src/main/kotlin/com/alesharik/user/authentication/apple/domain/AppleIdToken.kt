package com.alesharik.user.authentication.apple.domain

data class AppleIdToken(
    val subject: String,
    val email: String?,
    val emailVerified: Boolean
)
