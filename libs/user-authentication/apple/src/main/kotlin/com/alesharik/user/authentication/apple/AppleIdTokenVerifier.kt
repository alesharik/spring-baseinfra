package com.alesharik.user.authentication.apple

import com.alesharik.user.authentication.apple.domain.AppleIdToken
import com.alesharik.user.authentication.exceptions.BadIdTokenException
import com.alesharik.user.authentication.exceptions.EmailNotVerifiedException
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.source.RemoteJWKSet
import com.nimbusds.jose.proc.JWSAlgorithmFamilyJWSKeySelector
import com.nimbusds.jose.proc.SecurityContext
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier
import com.nimbusds.jwt.proc.DefaultJWTProcessor
import org.springframework.security.oauth2.jwt.JwtException
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import java.net.URI

class AppleIdTokenVerifier(audience: Set<String>, private val verifiedEmailRequired: Boolean = false) {
    private val decoder: NimbusJwtDecoder

    init {
        val jwkSet = RemoteJWKSet<SecurityContext>(URI.create("https://appleid.apple.com/auth/keys").toURL())
        val jwtProcessor = DefaultJWTProcessor<SecurityContext>()
        jwtProcessor.jwsKeySelector = JWSAlgorithmFamilyJWSKeySelector(JWSAlgorithm.Family.RSA, jwkSet)
        jwtProcessor.jwtClaimsSetVerifier = DefaultJWTClaimsVerifier(
            audience,
            JWTClaimsSet.Builder()
                .issuer("https://appleid.apple.com")
                .build(),
            setOf("sub", "c_hash", "email", "auth_time"),
            null
        )
        decoder = NimbusJwtDecoder(jwtProcessor)
    }

    fun verify(token: String): AppleIdToken {
        try {
            val parsed = decoder.decode(token)
            val idToken = AppleIdToken(
                parsed.subject,
                parsed.getClaimAsString("email"),
                parsed.getClaimAsBoolean("email_verified")
            )
            if (verifiedEmailRequired && (idToken.email.isNullOrEmpty() || !idToken.emailVerified)) {
                throw EmailNotVerifiedException()
            }
            return idToken
        } catch (e: JwtException) {
            throw BadIdTokenException("apple")
        }
    }
}
