package com.alesharik.country.provider.hardcoded;

import com.alesharik.country.provider.CountryProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class HardcodedCountryProviderConfiguration {
    @Bean
    public CountryProvider hardcodedCountryProvider() {
        return new HardcodedCountryProvider();
    }
}
