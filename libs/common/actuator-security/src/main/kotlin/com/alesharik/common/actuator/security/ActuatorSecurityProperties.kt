package com.alesharik.common.actuator.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties("management.security")
open class ActuatorSecurityProperties @ConstructorBinding constructor(
    val username: String,
    val password: String
)
