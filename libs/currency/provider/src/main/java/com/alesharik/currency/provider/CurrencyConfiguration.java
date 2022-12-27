package com.alesharik.currency.provider;

import com.alesharik.currency.provider.controller.CurrencyController;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class CurrencyConfiguration {
    @Bean
    public CurrencyController currencyController(CurrencyProvider countryProvider) {
        return new CurrencyController(countryProvider);
    }
}
