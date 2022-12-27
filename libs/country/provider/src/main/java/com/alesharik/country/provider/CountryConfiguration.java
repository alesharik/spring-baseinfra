package com.alesharik.country.provider;

import com.alesharik.country.provider.controller.CountryController;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class CountryConfiguration {
    @Bean
    public CountryController countryController(CountryProvider countryProvider) {
        return new CountryController(countryProvider);
    }
}
