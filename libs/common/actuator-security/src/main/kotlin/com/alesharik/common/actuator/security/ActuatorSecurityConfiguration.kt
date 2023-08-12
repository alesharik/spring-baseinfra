package com.alesharik.common.actuator.security

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter
import org.springframework.boot.context.TypeExcludeFilter
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType

@AutoConfiguration
@EnableConfigurationProperties(ActuatorSecurityProperties::class)
@ComponentScan(
    excludeFilters = [ComponentScan.Filter(
        type = FilterType.CUSTOM,
        classes = [TypeExcludeFilter::class]
    ), ComponentScan.Filter(type = FilterType.CUSTOM, classes = [AutoConfigurationExcludeFilter::class])]
)
open class ActuatorSecurityConfiguration
