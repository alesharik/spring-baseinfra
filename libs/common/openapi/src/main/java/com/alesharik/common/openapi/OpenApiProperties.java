package com.alesharik.common.openapi;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("openapi")
public class OpenApiProperties {
    private String username;
    private String password;
}
