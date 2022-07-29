package com.alesharik.spring.file.storage.local.selfhosted;

import com.alesharik.spring.file.storage.FileStorage;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(LocalSelfHostedFileStorageProperties.class)
public class LocalSelfHostedFileStorageConfiguration {
    @Bean
    @ConditionalOnMissingBean(FileStorage.class)
    public FileStorage localSelfHostedFileStorage(LocalSelfHostedFileStorageProperties properties) {
        return new LocalSelfHostedFileStorage(properties);
    }

    @Bean
    @ConditionalOnMissingBean(StaticController.class)
    public StaticController localSelfHostedController(FileStorage storage) {
        return new StaticController(storage);
    }
}
