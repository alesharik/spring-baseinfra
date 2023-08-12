package com.alesharik.common.actuator.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

open class ActuatorSecurityAuthenticationFilter(authenticationManager: AuthenticationManager?) :
    BasicAuthenticationFilter(authenticationManager, BasicAuthenticationEntryPoint()) {
    private val matcher: AntPathRequestMatcher = AntPathRequestMatcher("/actuator/**")
    private val authenticationConverter: BasicAuthenticationConverter = BasicAuthenticationConverter()

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        if (!matcher.matches(request)) {
            chain.doFilter(request, response)
            return
        }
        if (authenticationConverter.convert(request) == null) {
            val exception = AuthenticationCredentialsNotFoundException("Actuator requires authentication")
            SecurityContextHolder.clearContext()
            this.logger.debug("Failed to process authentication request", exception)
            onUnsuccessfulAuthentication(request, response, exception)
            authenticationEntryPoint.commence(request, response, exception)
            return
        }
        super.doFilterInternal(request, response, chain)
    }
}

fun HttpSecurity.setupActuatorSecurity(properties: ActuatorSecurityProperties): HttpSecurity {
    addFilter(ActuatorSecurityAuthenticationFilter(ActuatorSecurityManager(properties)))
    return this
}
