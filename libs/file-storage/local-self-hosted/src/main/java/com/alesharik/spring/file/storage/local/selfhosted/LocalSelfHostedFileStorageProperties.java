package com.alesharik.spring.file.storage.local.selfhosted;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("storage.local.selfhosted")
public class LocalSelfHostedFileStorageProperties {
    private String path;
    private String basePath;
}
