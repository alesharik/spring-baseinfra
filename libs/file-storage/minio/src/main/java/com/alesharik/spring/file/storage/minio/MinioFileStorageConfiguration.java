package com.alesharik.spring.file.storage.minio;

import com.alesharik.spring.common.minio.MinioClientTemplate;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(MinioFileStorageProperties.class)
public class MinioFileStorageConfiguration {
    @Bean
    public MinioFileStorage minioFileStorage(MinioClientTemplate template, MinioFileStorageProperties properties) {
        return new MinioFileStorage(properties, template);
    }
}
