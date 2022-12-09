package com.alesharik.common.mail;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("spring.mail")
public class EmailProperties {
    private String from;
    private String senderName = null;
}
