package com.alesharik.spring.file.storage.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("storage.minio")
public class MinioFileStorageProperties {
    private String bucket;
    private String baseBucketPath;
}
