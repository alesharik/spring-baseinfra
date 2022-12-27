package com.alesharik.currency.provider.hardcoded;

import com.alesharik.currency.provider.CurrencyProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class HardcodedCurrencyProviderConfiguration {
    @Bean
    public CurrencyProvider hardcodedCurrencyProvider() {
        return new HardcodedCurrencyProvider();
    }
}
