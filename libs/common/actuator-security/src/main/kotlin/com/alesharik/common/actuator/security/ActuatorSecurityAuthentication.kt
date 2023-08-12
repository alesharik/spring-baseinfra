package com.alesharik.common.actuator.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

open class ActuatorSecurityAuthentication : Authentication {
    override fun getAuthorities() = listOf(GrantedAuthority { ROLE })
    override fun getPrincipal(): Any = "actuator"
    override fun getCredentials(): Any? = null
    override fun getDetails(): Any? = null
    override fun isAuthenticated(): Boolean = true
    override fun setAuthenticated(isAuthenticated: Boolean) {}
    override fun getName(): String = "actuator"

    companion object {
        const val ROLE = "ROLE_ACTUATOR"
    }
}
