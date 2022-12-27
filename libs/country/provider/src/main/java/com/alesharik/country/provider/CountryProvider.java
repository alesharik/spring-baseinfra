package com.alesharik.country.provider;

import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Country provider
 */
public interface CountryProvider {
    /**
     * Get all countries
     * @return all countries
     */
    @NonNull
    List<Country> getCountries();

    /**
     * Get country by code
     * @param code alpha-2 code
     * @return country
     * @throws com.alesharik.country.provider.exception.CountryNotFoundException if country not found
     */
    @NonNull
    Country getCountry(@NonNull String code);

    /**
     * Check for country existence
     * @param code alpha-2 code
     * @return <code>true</code> - country exists
     */
    boolean hasCountry(@NonNull String code);
}
