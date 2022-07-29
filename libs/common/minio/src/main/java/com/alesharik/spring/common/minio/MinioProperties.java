package com.alesharik.spring.common.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("minio")
public class MinioProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
}
