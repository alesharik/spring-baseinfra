package com.alesharik.common.actuator.security

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException

open class ActuatorSecurityManager(
    private val actuatorSecurityProperties: ActuatorSecurityProperties
) : AuthenticationManager {

    override fun authenticate(authentication: Authentication): Authentication {
        return if (authentication is UsernamePasswordAuthenticationToken) {
            val login = authentication.getPrincipal() as String
            val password = authentication.getCredentials() as String
            if (actuatorSecurityProperties.username != login) throw UsernameNotFoundException("Username not found")
            if (actuatorSecurityProperties.password != password) throw BadCredentialsException("Password does not match")
            ActuatorSecurityAuthentication()
        } else {
            throw BadCredentialsException("Actuator requires basic authentication")
        }
    }
}
