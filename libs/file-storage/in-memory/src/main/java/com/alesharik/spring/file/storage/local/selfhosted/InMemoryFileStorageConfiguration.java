package com.alesharik.spring.file.storage.local.selfhosted;

import com.alesharik.spring.file.storage.FileStorage;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class InMemoryFileStorageConfiguration {
    @Bean
    @ConditionalOnMissingBean(FileStorage.class)
    public FileStorage inMemoryFileStorage() {
        return new InMemoryFileStorage();
    }

    @Bean
    @ConditionalOnMissingBean(StaticController.class)
    public StaticController inMemoryController(FileStorage storage) {
        return new StaticController(storage);
    }
}
